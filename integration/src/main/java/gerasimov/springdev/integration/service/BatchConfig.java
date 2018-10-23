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

import static gerasimov.springdev.integration.service.ChannelsConfig.INCORRECT_ORDERS;
import static gerasimov.springdev.integration.service.ChannelsConfig.ORDINARY_ORDERS;

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
    ItemReader<Order> ordinaryReader(@Qualifier(ORDINARY_ORDERS) PollableChannel messageChannel) {
        return () -> (Order) messageChannel.receive().getPayload();
    }

    @Bean
    ItemReader<Order> incorrectReader(@Qualifier(INCORRECT_ORDERS) PollableChannel messageChannel) {
        return () -> (Order) messageChannel.receive().getPayload();
    }

    @Bean
    ItemProcessor<Order, String> toStringItemProcessor() {
        return Order::toString;
    }

    @Bean
    public FlatFileItemWriter<String> writerOrdinary(
            @Value("${integration.output.dir}") String outputDir,
            @Value("${integration.output.ordinary}") String fileName) {
        return new FlatFileItemWriterBuilder<String>()
                .name("orderWriter")
                .resource(new FileSystemResource(new File(new File(outputDir), fileName)))
                .lineAggregator(new DelimitedLineAggregator<>())
                .build();
    }

    @Bean
    public FlatFileItemWriter<String> writerIncorrect(
            @Value("${integration.output.dir}") String outputDir,
            @Value("${integration.output.incorrect}") String fileName) {
        return new FlatFileItemWriterBuilder<String>()
                .name("incorrectOrderWriter")
                .resource(new FileSystemResource(new File(new File(outputDir), fileName)))
                .lineAggregator(new DelimitedLineAggregator<>())
                .build();
    }

    @Bean
    public Step stepOrdinary(@Qualifier("ordinaryReader") ItemReader<Order> reader, ItemProcessor processor, @Qualifier("writerOrdinary") ItemWriter<String> writer) {
        return stepBuilderFactory.get("stepOrdinary")
                .chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step stepIncorrect(@Qualifier("incorrectReader") ItemReader<Order> reader, ItemProcessor processor, @Qualifier("writerIncorrect") ItemWriter<String> writer) {
        return stepBuilderFactory.get("stepIncorrect")
                .chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job writeOrdinary(@Qualifier("stepOrdinary") Step step) {
        return jobBuilderFactory.get("ordinary")
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Job writeIncorrect(@Qualifier("stepIncorrect") Step step) {
        return jobBuilderFactory.get("incorrect")
                .flow(step)
                .end()
                .build();
    }
}
