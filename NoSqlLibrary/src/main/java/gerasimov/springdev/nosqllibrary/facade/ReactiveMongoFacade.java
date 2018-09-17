package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.reactive.ReactiveBookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
    public Mono<String> addBook(Book book) {
        return bookRepository.save(book).map(book1 -> book.getId());
    }

    @Override
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id);
    }

    @Override
    public Mono<Void> updateBook(Book book) {
        return bookRepository.save(book).then();
    }
}
