package gerasimov.springdev.nosqllibrary.facade;


import gerasimov.springdev.nosqllibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface LibraryFacade {
    void addBook(String title, List<String> Authors, List<String> genres);

    String findBook(String title);

    void commentBook(String bookId, String text);

    Optional<Book> showBookInfo(String bookId);

    List<Book> allBooks();

    void deleteBook(String id);

    void updateBook(Book book);
}
