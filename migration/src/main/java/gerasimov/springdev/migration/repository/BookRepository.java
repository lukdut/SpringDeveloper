package gerasimov.springdev.migration.repository;

import gerasimov.springdev.migration.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findFirstByTitle(String title);
}

