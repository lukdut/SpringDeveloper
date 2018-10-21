package gerasimov.springdev.integration;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;
import gerasimov.springdev.integration.service.OrderGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@IntegrationComponentScan
public class IntegrationApplication {
    private static volatile long counter = 0;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationApplication.class, args);

        OrderGateway orderGateway = ctx.getBean(OrderGateway.class);


        new Thread(() -> {
            long prev;
            while (!Thread.interrupted()) {
                prev = counter;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long speed = counter - prev;
                System.out.println("Speed is " + speed + " events per second");
            }
        }, "measureThread").start();

        while (!Thread.interrupted()) {
            orderGateway.orderReceiver(generateOrder());
            counter++;
        }
    }

    private static Order generateOrder() {
        int amountOfPositions = ThreadLocalRandom.current().nextInt(10, 100);

        List<OrderPosition> orderPositions = new ArrayList<>(amountOfPositions);

        for (int i = 0; i < amountOfPositions; i++) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setCount(ThreadLocalRandom.current().nextInt(-2, 10));
            orderPosition.setPrice(ThreadLocalRandom.current().nextDouble(-100, 1500));
            orderPosition.setItem("Goods");
        }

        Order order = new Order();
        order.setPositions(orderPositions);
        return order;
    }

    @Bean
    public IntegrationFlow order() {
        return f -> f.transform(
                o -> false);
    }
}
