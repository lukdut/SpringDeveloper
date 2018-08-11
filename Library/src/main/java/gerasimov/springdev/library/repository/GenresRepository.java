package gerasimov.springdev.library.repository;

import gerasimov.springdev.library.model.Genre;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface GenresRepository extends Repository<Genre, UUID> {
    void save(Genre author);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();
}
