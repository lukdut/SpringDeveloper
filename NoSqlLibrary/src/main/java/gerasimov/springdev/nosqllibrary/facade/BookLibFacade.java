package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;

import java.util.List;

public interface BookLibFacade {
    List<Book> allBooks();

    void deleteBook(String id);

    void updateBook(Book book);

    String addBook(Book book);
}
