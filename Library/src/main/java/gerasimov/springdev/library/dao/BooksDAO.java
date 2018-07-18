package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Book;

import java.util.List;

public interface BooksDAO {
    List<Book> getAll();
    void addBook(String title, String authors, String genres);
}
