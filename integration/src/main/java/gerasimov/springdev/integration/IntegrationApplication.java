package gerasimov.springdev.integration;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;
import gerasimov.springdev.integration.service.OrderGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@IntegrationComponentScan
public class IntegrationApplication {
    private static AtomicLong counter = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationApplication.class, args);

        OrderGateway orderGateway = ctx.getBean(OrderGateway.class);


        new Thread(() -> {
            long prev;
            while (!Thread.interrupted()) {
                prev = counter.get();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long speed = counter.get() - prev;
                System.out.println("Speed is " + speed + " events per second");
            }
        }, "measureThread").start();

        while (!Thread.interrupted()) {
            orderGateway.orderReceiver(generateOrder());
            counter.incrementAndGet();
        }
    }

    private static Order generateOrder() {
        int amountOfPositions = ThreadLocalRandom.current().nextInt(1, 15);

        List<OrderPosition> orderPositions = new ArrayList<>(amountOfPositions);

        for (int i = 0; i < amountOfPositions; i++) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setCount(ThreadLocalRandom.current().nextInt(-2, 10));
            orderPosition.setPrice(ThreadLocalRandom.current().nextDouble(-100, 1500));
            orderPosition.setItem("Goods");
            orderPositions.add(orderPosition);
        }

        Order order = new Order();
        order.setPositions(orderPositions);
        return order;
    }
}
