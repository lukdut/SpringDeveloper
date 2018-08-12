package gerasimov.springdev.nosqllibrary.repository;

import gerasimov.springdev.nosqllibrary.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenresRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByNameIgnoreCase(String name);
}
