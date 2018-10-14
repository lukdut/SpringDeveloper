package gerasimov.springdev.migration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

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
    ItemReader<BookDTO> dbItemReader(DataSource dataSource) {
        JdbcCursorItemReader<BookDTO> itemReader = new JdbcCursorItemReader<>();

        itemReader.setDataSource(dataSource);
        itemReader.setSql("select * from books");
        itemReader.setRowMapper(new BeanPropertyRowMapper<>(BookDTO.class));

        return itemReader;
    }

    @Bean
    ItemProcessor<BookDTO, String> stringVoidItemProcessor() {
        return new ItemProcessor<BookDTO, String>() {
            @Override
            public String process(BookDTO s) {
                return s.getTitle();
            }
        };
    }

    @Bean
    ItemWriter<String> voidItemWriter() {
        return list -> System.out.println("processed: " + list);
    }

    @Bean
    public Step step(ItemReader<BookDTO> reader, ItemProcessor processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("step")
                .chunk(3)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job migrate(Step step) {
        return jobBuilderFactory.get("migration")
                .flow(step)
                .end()
                .build();
    }
}
