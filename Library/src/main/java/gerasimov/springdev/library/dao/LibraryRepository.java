package gerasimov.springdev.library.dao;


import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends BooksDAO {
    Genre addGenre(String name);

    Author addAuthor(String name);

    void addBook(String title, List<String> authors, List<String> genres);

    Optional<Author> findAuthor(String name);

    Optional<Genre> findGenre(String name);

    List<Book> findBooks(String title);
}
