package gerasimov.springdev.migration;

import gerasimov.springdev.migration.model.Book;
import gerasimov.springdev.migration.model.BookDTO;
import gerasimov.springdev.migration.repository.BookRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
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
import java.util.stream.Collectors;

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
    ItemProcessor<BookDTO, Book> stringVoidItemProcessor() {
        return bookDTO -> new Book(bookDTO.getTitle());
    }

    @Bean
    ItemWriter<Book> voidItemWriter(BookRepository bookRepository) {
        return list -> bookRepository.saveAll(list.stream()
                .filter(item -> !bookRepository.findFirstByTitle(item.getTitle()).isPresent())
                .collect(Collectors.toList()));
    }

    @Bean
    public Step step(ItemReader<BookDTO> reader, ItemProcessor processor, ItemWriter<Book> writer) {
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
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("Starting processing");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("Processing completed");
                    }
                })
                .build();
    }
}
