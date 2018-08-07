package gerasimov.springdev.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryApplication {
    //add book --authors author --genres genre
    public static void main(String[] args) {
        //Console.main(args);
        SpringApplication.run(LibraryApplication.class, args);
    }
}
