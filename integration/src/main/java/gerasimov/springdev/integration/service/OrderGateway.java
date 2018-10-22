package gerasimov.springdev.integration.service;

import gerasimov.springdev.integration.model.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OrderGateway {
    @Gateway(requestChannel = "orderChannel")
    void orderReceiver(Order order);
}
