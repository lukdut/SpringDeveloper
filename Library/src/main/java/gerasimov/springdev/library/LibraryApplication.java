package gerasimov.springdev.library;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.sql.SQLException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryApplication {
    //add book --authors author --genres genre
    public static void main(String[] args) throws SQLException {
        Console.main(args);
        SpringApplication.run(LibraryApplication.class, args);
    }
}
