package gerasimov.springdev.integration.service;


import gerasimov.springdev.integration.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageHandler;

import java.io.File;

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
    public IntegrationFlow correctnessFlow() {
        return f -> f.channel(NON_EMPTY_ORDERS)
                .<Order, Boolean>route(
                        order -> order.getPositions().stream()
                                .filter(orderPosition -> orderPosition.getPrice() <= 0 || orderPosition.getCount() <= 0)
                                .findAny().isPresent(),
                        mapping -> {
                            mapping.subFlowMapping(true, sf -> sf.channel(INCORRECT_ORDERS))
                                    .subFlowMapping(false, sf -> sf.channel(CORRECT_ORDERS));
                        });
    }

    @Bean
    public IntegrationFlow vipOrdersFlow() {
        return f -> f.channel(CORRECT_ORDERS)
                .<Order, Boolean>route(
                        order -> order.getPositions().stream()
                                .mapToDouble(orderPosition -> orderPosition.getPrice() * orderPosition.getCount())
                                .sum() >= VIP_THRESHOLD,
                        mapping -> {
                            mapping.subFlowMapping(true, sf -> sf.channel(VIP_ORDERS))
                                    .subFlowMapping(false, sf -> sf.channel(ORDINARY_ORDERS));
                        });
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
