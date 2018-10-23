package gerasimov.springdev.integration.service;

import gerasimov.springdev.integration.model.Order;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.messaging.PollableChannel;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    ItemReader<Order> ordinaryReader(@Qualifier("ordinaryOrders") PollableChannel messageChannel) {
        return () -> (Order) messageChannel.receive().getPayload();
    }

    @Bean
    ItemProcessor<Order, String> toStringItemProcessor() {
        return Order::toString;
    }

    @Bean
    public FlatFileItemWriter<String> writer(
            @Value("${integration.output.dir}") String outputDir,
            @Value("${integration.output.ordinary}") String fileName) {
        return new FlatFileItemWriterBuilder<String>()
                .name("orderWriter")
                .resource(new FileSystemResource(new File(new File(outputDir), fileName)))
                .lineAggregator(new DelimitedLineAggregator<>())
                .build();
    }

    @Bean
    public Step step(ItemReader<Order> reader, ItemProcessor processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("step")
                .chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job write(Step step) {
        return jobBuilderFactory.get("migration")
                .flow(step)
                .end()
                .build();
    }
}
