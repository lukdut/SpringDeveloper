package gerasimov.springdev.library;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class DBUtils {
    private final NamedParameterJdbcOperations jdbcOperations;

    DBUtils(NamedParameterJdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    public void clearDB(){
        jdbcOperations.update("delete from AUTHORS_BOOKS", new HashMap<>());
        jdbcOperations.update("delete from GENRES_BOOKS", new HashMap<>());
        jdbcOperations.update("delete from GENRES", new HashMap<>());
        jdbcOperations.update("delete from AUTHORS", new HashMap<>());
        jdbcOperations.update("delete from BOOKS", new HashMap<>());
    }
}
