package gerasimov.springdev.integration.service;

import gerasimov.springdev.integration.model.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static gerasimov.springdev.integration.service.ChannelsConfig.ORDER_INPUT_CHANNEL;


@MessagingGateway
public interface OrderInputGateway {
    @Gateway(requestChannel = ORDER_INPUT_CHANNEL)
    void receiveOrder(Order order);
}
