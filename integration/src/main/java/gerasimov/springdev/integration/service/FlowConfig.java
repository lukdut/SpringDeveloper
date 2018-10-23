package gerasimov.springdev.integration.service;


import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static gerasimov.springdev.integration.IntegrationApplication.VIP_THRESHOLD;
import static gerasimov.springdev.integration.service.ChannelsConfig.*;

@Configuration
public class FlowConfig {
    @Bean
    public IntegrationFlow orderFlow() {
        return f -> f.channel(ORDER_INPUT_CHANNEL)
                .<Order>filter(o -> o.getPositions() != null && !o.getPositions().isEmpty())
                .channel(NON_EMPTY_ORDERS);
    }

    @Bean
    @Router(inputChannel = NON_EMPTY_ORDERS)
    public AbstractMessageRouter correctnessRouter(
            @Qualifier("correctOrders") MessageChannel ok,
            @Qualifier("incorrectOrders") MessageChannel incorrect) {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                final Order payload = (Order) message.getPayload();
                final Optional<OrderPosition> negativeAmount = payload.getPositions().stream()
                        .filter(orderPosition -> orderPosition.getPrice() <= 0 || orderPosition.getCount() <= 0)
                        .findAny();
                if (negativeAmount.isPresent()) {
                    return Collections.singletonList(incorrect);
                } else {
                    return Collections.singletonList(ok);
                }
            }
        };
    }

    @Bean
    @Router(inputChannel = CORRECT_ORDERS)
    public AbstractMessageRouter vipRouter(
            @Qualifier("ordinaryOrders") MessageChannel ordinary,
            @Qualifier("vipOrders") MessageChannel vip) {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                final Order payload = (Order) message.getPayload();
                double totalAmount = payload.getPositions().stream()
                        .mapToDouble(orderPosition -> orderPosition.getPrice() * orderPosition.getCount())
                        .sum();
                if (totalAmount >= VIP_THRESHOLD) {
                    return Collections.singletonList(vip);
                } else {
                    return Collections.singletonList(ordinary);
                }
            }
        };
    }

    @Bean
    public IntegrationFlow vipFlow(
            @Value("${integration.output.dir}") String outputDir,
            @Value("${integration.output.vip}") String fileName,
            GenericTransformer<Order, String> orderToStringTransformer) {
        return f -> f.channel(VIP_ORDERS)
                .transform(orderToStringTransformer)
                .handle(getMessageWriterHandler(outputDir, fileName));
    }

    @Bean
    public GenericTransformer<Order, String> orderToStringTransformer() {
        return Order::toString;
    }


    private MessageHandler getMessageWriterHandler(String outputDir, String fileName) {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(outputDir));
        handler.setFileNameGenerator(message -> fileName);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        handler.setAppendNewLine(true);
        return handler;
    }
}
