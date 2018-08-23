package gerasimov.springdev.library.repository;

import gerasimov.springdev.library.model.Book;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface BooksRepository extends Repository<Book, UUID> {
    void save(Book book);

    List<Book> findAll();

    List<Book> findAllByTitle(String title);

    Book findById(UUID uuid);
}
