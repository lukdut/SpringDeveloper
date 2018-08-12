package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Collections;

@SpringBootApplication
public class NoSqlLibraryApplication {
    @Autowired
    LibraryFacade authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(NoSqlLibraryApplication.class, args);
    }

    @PostConstruct
    public void test() {
        authorRepository.addBook("Война и мир", Collections.singletonList("Лев толстой"), Collections.singletonList("роман"));
        authorRepository.addBook("Spring in action", Collections.singletonList("Craig Walls"), Collections.emptyList());
    }
}
