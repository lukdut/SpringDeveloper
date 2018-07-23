package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;

import java.util.List;

public interface BooksDAO {
    List<Book> getAll();

    void addBook(String title, List<String> authors, List<String> genres);

    List<Author> getAuthors();

    List<Genre> getGenres();
}
