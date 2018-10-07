package gerasimov.springdev.nosqllibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:application.yml")
@EnableJpaRepositories
public class NoSqlLibraryApplication {
    //add book --authors author --genres genre
    public static void main(String[] args) {
        SpringApplication.run(NoSqlLibraryApplication.class, args);
    }
}
