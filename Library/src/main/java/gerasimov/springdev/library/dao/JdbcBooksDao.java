package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.LogAfter;
import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Repository
public class JdbcBooksDao implements BooksDAO {
    private static final Object AUTHORS_MONITOR = new Object();
    private static final Object GENRES_MONITOR = new Object();

    private final NamedParameterJdbcOperations jdbc;

    public JdbcBooksDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @LogAfter("book list obtained")
    public List<Book> getAll() {
        List<UUID[]> mapRows = jdbc.query("Select * from GENRES_BOOKS", (rs, i) -> {
            UUID uuids[] = new UUID[2];
            uuids[0] = UUID.fromString(rs.getString("BOOK_ID"));
            uuids[1] = UUID.fromString(rs.getString("GENRE_ID"));
            return uuids;
        });

        final Map<UUID, Set<UUID>> genresForBook = mapRows.stream().collect(Collectors.groupingBy(o -> o[0], Collectors.mapping(o -> o[1], Collectors.toSet())));

        mapRows = jdbc.query("Select * from AUTHORS_BOOKS", (rs, i) -> {
            UUID uuids[] = new UUID[2];
            uuids[0] = UUID.fromString(rs.getString("BOOK_ID"));
            uuids[1] = UUID.fromString(rs.getString("AUTHOR_ID"));
            return uuids;
        });
        final Map<UUID, Set<UUID>> authorsForBook = mapRows.stream().collect(Collectors.groupingBy(o -> o[0], Collectors.mapping(o -> o[1], Collectors.toSet())));

        final Map<UUID, Genre> knownGenres = jdbc.query("select * from genres",
                (rs, i) -> new Genre(UUID.fromString(rs.getString("id")), rs.getString("name"))).stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));

        final Map<UUID, Author> knownAuthors = jdbc.query("select * from authors",
                (rs, i) -> new Author(UUID.fromString(rs.getString("id")), rs.getString("name"))).stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));

        return jdbc.query("Select * from books", (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String tittle = resultSet.getString("title");

            final List<Genre> genres = genresForBook.get(id).stream()
                    .map(knownGenres::get)
                    .collect(Collectors.toList());

            final List<Author> authors = authorsForBook.get(id).stream()
                    .map(knownAuthors::get)
                    .collect(Collectors.toList());

            return new Book(id, tittle, authors, genres);
        });
    }

    @Override
    public void addBook(String title, List<String> authors, List<String> genres) {
        final List<Author> authorsList = authors.stream()
                .map(this::storeAuthor)
                .collect(Collectors.toList());
        final List<Genre> genresList = genres.stream()
                .map(this::storeGenre)
                .collect(Collectors.toList());

        UUID bookId = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", bookId);
        params.put("title", title);
        jdbc.update("INSERT INTO books (id, title) VALUES (:id, :title)", params);

        authorsList.forEach(author -> {
            Map<String, Object> authorParams = new HashMap<>(2);
            authorParams.put("author", author.getId());
            authorParams.put("book", bookId);
            jdbc.update("INSERT INTO AUTHORS_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (:author, :book)", authorParams);
        });

        genresList.forEach(genre -> {
            Map<String, Object> genresParams = new HashMap<>(2);
            genresParams.put("genre", genre.getId());
            genresParams.put("book", bookId);
            jdbc.update("INSERT INTO GENRES_BOOKS (GENRE_ID, BOOK_ID) VALUES (:genre, :book)", genresParams);
        });
    }

    @Override
    public List<Author> getAuthors() {
        return jdbc.query("Select * from authors", (rs, i) -> new Author(UUID.fromString(rs.getString("id")), rs.getString("name")));
    }

    @Override
    public List<Genre> getGenres() {
        return jdbc.query("Select * from genres", (rs, i) -> new Genre(UUID.fromString(rs.getString("id")), rs.getString("name")));
    }

    private synchronized Genre storeGenre(String genreName) {
        final Map<String, Object> param = new HashMap<>(1);
        param.put("name", genreName);

        synchronized (GENRES_MONITOR) {
            Integer cnt = jdbc.queryForObject("SELECT count(*) FROM genres WHERE name=:name", param, Integer.class);
            if (cnt <= 0) {
                UUID id = UUID.randomUUID();
                param.put("id", id);
                jdbc.update("INSERT INTO genres (id, name) VALUES (:id, :name)", param);
                return new Genre(id, genreName);
            }
        }

        return jdbc.queryForObject("SELECT * FROM genres WHERE name=:name", param, (rs, i) ->
                new Genre(UUID.fromString(rs.getString("id")), rs.getString("name"))
        );
    }

    private synchronized Author storeAuthor(String authorName) {
        final Map<String, Object> param = new HashMap<>(1);
        param.put("name", authorName);

        synchronized (AUTHORS_MONITOR) {
            Integer cnt = jdbc.queryForObject("SELECT count(*) FROM authors WHERE name=:name", param, Integer.class);
            if (cnt <= 0) {
                UUID id = UUID.randomUUID();
                param.put("id", id);
                jdbc.update("INSERT INTO authors (id, name) VALUES (:id, :name)", param);
                return new Author(id, authorName);
            }
        }

        return jdbc.queryForObject("SELECT * FROM authors WHERE name=:name", param, (rs, i) ->
                new Author(UUID.fromString(rs.getString("id")), rs.getString("name"))
        );
    }
}
