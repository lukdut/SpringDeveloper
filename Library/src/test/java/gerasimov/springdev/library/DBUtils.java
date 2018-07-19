package gerasimov.springdev.library;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Genre;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class DBUtils {
    private final NamedParameterJdbcOperations jdbcOperations;

    DBUtils(NamedParameterJdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    public List<Author> getKnownAuthors(){
        return jdbcOperations.query("Select * from authors", (rs, i) -> new Author(UUID.fromString(rs.getString("id")), rs.getString("name")));
    }

    public List<Genre> getKnownGenres(){
        return jdbcOperations.query("Select * from genres", (rs, i) -> new Genre(UUID.fromString(rs.getString("id")), rs.getString("name")));
    }

    public void clearDB(){
        jdbcOperations.update("delete from AUTHORS_BOOKS", new HashMap<>());
        jdbcOperations.update("delete from GENRES_BOOKS", new HashMap<>());
        jdbcOperations.update("delete from GENRES", new HashMap<>());
        jdbcOperations.update("delete from AUTHORS", new HashMap<>());
        jdbcOperations.update("delete from BOOKS", new HashMap<>());
    }
}
