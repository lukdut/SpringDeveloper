package gerasimov.springdev.integration;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;
import gerasimov.springdev.integration.service.OrderGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@IntegrationComponentScan
public class IntegrationApplication {
    private static AtomicLong counter = new AtomicLong();

    public static void main(String[] args) {
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

    @Bean
    //@ServiceActivator(inputChannel= "fileChannel")
    public MessageHandler fileWritingMessageHandler(@Value("${integration.output.all}") String outputDir) {
        System.out.println("Using directory " + outputDir);
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(outputDir));
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public IntegrationFlow order(@Qualifier("fileWritingMessageHandler") MessageHandler fileWriter) {
        return f -> f
                //.log()
                .transform(Order::getPositions)
                .split()
                .transform(o -> o.toString().getBytes())
                //.log()
                //.handle(fileWriter)
                .transform(o -> false);
                /*


                .transform(
                o -> false);*/
    }
}
