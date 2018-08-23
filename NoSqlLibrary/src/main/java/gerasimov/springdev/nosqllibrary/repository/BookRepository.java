package gerasimov.springdev.nosqllibrary.repository;

import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleLike(String title);

    Optional<Book> findByTitleIgnoreCase(String title);
}
