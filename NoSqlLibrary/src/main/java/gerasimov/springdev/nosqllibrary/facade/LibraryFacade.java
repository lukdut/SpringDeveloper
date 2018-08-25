package gerasimov.springdev.nosqllibrary.facade;


import gerasimov.springdev.nosqllibrary.model.Author;
import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.model.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryFacade {
    void addBook(String title, List<String> Authors, List<String> genres);

    String findBook(String title);

    void commentBook(String bookId, String text);

    Optional<Book> showBookInfo(String bookId);

    List<Book> allBooks();

    void deleteBook(String id);

    Optional<Author> getAuthor(String id);

    Optional<Genre> getGenre(String id);

    void updateBook(Book book);
}
