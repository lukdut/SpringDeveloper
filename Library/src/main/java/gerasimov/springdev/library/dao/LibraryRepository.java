package gerasimov.springdev.library.dao;


import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryRepository {
    Genre addGenre(String name);

    Author addAuthor(String name);

    Book addBook(String title, List<UUID> authors, List<UUID> genres);


    Optional<Author> findAuthor(String name);

    Optional<Genre> findGenre(String name);

    Optional<List<Book>> findBooks(String title);
}
