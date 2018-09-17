package gerasimov.springdev.nosqllibrary.repository.reactive;

import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveBookRepository extends ReactiveCrudRepository<Book, String> {
}
