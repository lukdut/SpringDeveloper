package gerasimov.springdev.integration.util;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;

import java.util.Collections;

public class TestOrderProvider {
    public static Order onePosOrder(int count, double price){
        Order order = new Order();
        final OrderPosition orderPosition = new OrderPosition();
        orderPosition.setCount(count);
        orderPosition.setPrice(price);
        orderPosition.setItem("Test Item");
        order.setPositions(Collections.singletonList(orderPosition));
        return order;
    }
}
