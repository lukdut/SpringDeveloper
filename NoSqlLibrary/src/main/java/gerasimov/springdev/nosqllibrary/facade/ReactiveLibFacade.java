package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import reactor.core.publisher.Flux;

public interface ReactiveLibFacade {
    Flux<Book> allBooks();

    Flux<String> addBook(Book book);

    Flux<Void> deleteBook(String id);

    Flux<Void> updateBook(Book book);
}
