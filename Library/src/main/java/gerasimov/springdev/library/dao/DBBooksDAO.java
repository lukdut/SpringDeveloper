package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DBBooksDAO implements BooksDAO {
    private static final String TITLE = "title";
    private static final String ID = "id";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String GENRE_ID = "GENRE_ID";
    private static final String AUTHOR_ID = "AUTHOR_ID";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String BOOK = "book";
    private static final String GENRE = "genre";

    private static final Object AUTHORS_MONITOR = new Object();
    private static final Object GENRES_MONITOR = new Object();


    private final NamedParameterJdbcOperations jdbc;

    public DBBooksDAO(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> getAll() {
        List<UUID[]> mapRows = jdbc.query("Select * from GENRES_BOOKS", (rs, i) -> {
            UUID uuids[] = new UUID[2];
            uuids[0] = UUID.fromString(rs.getString(BOOK_ID));
            uuids[1] = UUID.fromString(rs.getString(GENRE_ID));
            return uuids;
        });

        final Map<UUID, Set<UUID>> genresForBook = mapRows.stream().collect(Collectors.groupingBy(o -> o[0], Collectors.mapping(o -> o[1], Collectors.toSet())));

        mapRows = jdbc.query("Select * from AUTHORS_BOOKS", (rs, i) -> {
            UUID uuids[] = new UUID[2];
            uuids[0] = UUID.fromString(rs.getString(BOOK_ID));
            uuids[1] = UUID.fromString(rs.getString(AUTHOR_ID));
            return uuids;
        });
        final Map<UUID, Set<UUID>> authorsForBook = mapRows.stream().collect(Collectors.groupingBy(o -> o[0], Collectors.mapping(o -> o[1], Collectors.toSet())));

        final Map<UUID, Genre> knownGenres = jdbc.query("select * from genres",
                (rs, i) -> new Genre(UUID.fromString(rs.getString(ID)), rs.getString(NAME))).stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));

        final Map<UUID, Author> knownAuthors = jdbc.query("select * from authors",
                (rs, i) -> new Author(UUID.fromString(rs.getString(ID)), rs.getString(NAME))).stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));

        return jdbc.query("Select * from books", (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString(ID));
            String tittle = resultSet.getString(TITLE);

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
    public void addBook(String title, String authors, String genres) {
        final List<Author> authorsList = Stream.of(authors.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .map(this::storeAuthor)
                .collect(Collectors.toList());
        final List<Genre> genresList = Stream.of(genres.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .map(this::storeGenre)
                .collect(Collectors.toList());

        UUID bookId = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>(2);
        params.put(ID, bookId);
        params.put(TITLE, title);
        jdbc.update("INSERT INTO books (id, title) VALUES (:id, :title)", params);

        authorsList.forEach(author -> {
            Map<String, Object> authorParams = new HashMap<>(2);
            authorParams.put(AUTHOR, author.getId());
            authorParams.put(BOOK, bookId);
            jdbc.update("INSERT INTO AUTHORS_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (:author, :book)", authorParams);
        });

        genresList.forEach(genre -> {
            Map<String, Object> genresParams = new HashMap<>(2);
            genresParams.put(GENRE, genre.getId());
            genresParams.put(BOOK, bookId);
            jdbc.update("INSERT INTO GENRES_BOOKS (GENRE_ID, BOOK_ID) VALUES (:genre, :book)", genresParams);
        });
    }

    private synchronized Genre storeGenre(String genreName) {
        final Map<String, Object> param = new HashMap<>(1);
        param.put(NAME, genreName);

        synchronized (GENRES_MONITOR) {
            Integer cnt = jdbc.queryForObject("SELECT count(*) FROM genres WHERE name=:name", param, Integer.class);
            if (cnt <= 0) {
                UUID id = UUID.randomUUID();
                param.put(ID, id);
                jdbc.update("INSERT INTO genres (id, name) VALUES (:id, :name)", param);
                return new Genre(id, genreName);
            }
        }

        return jdbc.queryForObject("SELECT * FROM genres WHERE name=:name", param, (rs, i) ->
                new Genre(UUID.fromString(rs.getString(ID)), rs.getString(NAME))
        );
    }

    private synchronized Author storeAuthor(String authorName) {
        final Map<String, Object> param = new HashMap<>(1);
        param.put(NAME, authorName);

        synchronized (AUTHORS_MONITOR) {
            Integer cnt = jdbc.queryForObject("SELECT count(*) FROM authors WHERE name=:name", param, Integer.class);
            if (cnt <= 0) {
                UUID id = UUID.randomUUID();
                param.put(ID, id);
                jdbc.update("INSERT INTO authors (id, name) VALUES (:id, :name)", param);
                return new Author(id, authorName);
            }
        }

        return jdbc.queryForObject("SELECT * FROM authors WHERE name=:name", param, (rs, i) ->
                new Author(UUID.fromString(rs.getString(ID)), rs.getString(NAME))
        );
    }
}
