package gerasimov.springdev.integration;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.model.OrderPosition;
import gerasimov.springdev.integration.service.OrderInputGateway;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
    public static double VIP_THRESHOLD = 90000;
    private static AtomicLong counter = new AtomicLong();

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationApplication.class, args);

        OrderInputGateway orderInputGateway = ctx.getBean(OrderInputGateway.class);

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

        startJobAsync(ctx, "writeOrdinary");
        startJobAsync(ctx, "writeIncorrect");

        while (!Thread.interrupted()) {
            orderInputGateway.receiveOrder(generateOrder());
            counter.incrementAndGet();
        }
    }

    private static void startJobAsync(ConfigurableApplicationContext ctx, String job) {
        new Thread(() -> {
            final JobLauncher launcher = ctx.getBean(JobLauncher.class);
            try {
                launcher.run(ctx.getBean(job, Job.class), new JobParameters());
            } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException | JobRestartException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static Order generateOrder() {
        int amountOfPositions = ThreadLocalRandom.current().nextInt(0, 15);

        List<OrderPosition> orderPositions = new ArrayList<>(amountOfPositions);

        for (int i = 0; i < amountOfPositions; i++) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setCount(ThreadLocalRandom.current().nextInt(1, 10));
            orderPosition.setPrice(ThreadLocalRandom.current().nextDouble(-10, 1500));
            orderPosition.setItem("Goods");
            orderPositions.add(orderPosition);
        }

        Order order = new Order();
        order.setPositions(orderPositions);
        return order;
    }
}
