package gerasimov.springdev.nosqllibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoSqlLibraryApplication {
    //add book --authors author --genres genre
    public static void main(String[] args) {
        SpringApplication.run(NoSqlLibraryApplication.class, args);
    }
}
