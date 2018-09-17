package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveLibFacade {
    Flux<Book> allBooks();

    Mono<String> addBook(Book book);

    Mono<Void> deleteBook(String id);

    Mono<Void> updateBook(Book book);
}
