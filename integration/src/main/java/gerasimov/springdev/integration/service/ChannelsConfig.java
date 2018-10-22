package gerasimov.springdev.integration.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;


@Configuration
public class ChannelsConfig {
    static final String ORDER_INPUT_CHANNEL = "orderInputChannel";
    static final String NON_EMPTY_ORDERS = "nonEmptyOrders";
    static final String INCORRECT_ORDERS = "incorrectOrders";
    static final String CORRECT_ORDERS = "correctOrders";


    @Bean
    public MessageChannel inputChannel() {
        return MessageChannels.direct(ORDER_INPUT_CHANNEL).get();
    }

    @Bean
    public MessageChannel nonEmptyOrders() {
        return MessageChannels.direct(NON_EMPTY_ORDERS).get();
    }

    @Bean
    public MessageChannel incorrectOrders() {
        return MessageChannels.direct(INCORRECT_ORDERS).get();
    }

    @Bean
    public MessageChannel correctOrders() {
        return MessageChannels.direct(CORRECT_ORDERS).get();
    }
}
