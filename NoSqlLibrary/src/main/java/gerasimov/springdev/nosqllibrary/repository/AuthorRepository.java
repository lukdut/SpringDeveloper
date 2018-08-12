package gerasimov.springdev.nosqllibrary.repository;

import gerasimov.springdev.nosqllibrary.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByFullNameIgnoreCase(String name);
}
