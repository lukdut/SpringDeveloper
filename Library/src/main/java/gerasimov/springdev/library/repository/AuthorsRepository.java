package gerasimov.springdev.library.repository;

import gerasimov.springdev.library.model.Author;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface AuthorsRepository extends Repository<Author, UUID> {
    void save(Author author);

    Optional<Author> findAuthorByFullName(String name);

    List<Author> findAll();
}
