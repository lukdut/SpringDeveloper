package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.reactive.ReactiveBookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class ReactiveMongoFacade implements ReactiveLibFacade {

    private final ReactiveBookRepository bookRepository;

    public ReactiveMongoFacade(ReactiveBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Flux<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<String> addBook(Book book) {
        return null;
    }

    @Override
    public Flux<Void> deleteBook(String id) {
        return null;
    }

    @Override
    public Flux<Void> updateBook(Book book) {
        return null;
    }
}
