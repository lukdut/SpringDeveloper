package gerasimov.springdev.library.model;

import java.util.List;
import java.util.UUID;

public class Book {
    private final UUID id;
    private final String title;
    private final List<Author> authors;
    private final List<Genre> genres;

    public Book(UUID id, String title, List<Author> authors, List<Genre> genres) {
        this.id = id;
        this.title = title.trim().toLowerCase();
        this.authors = authors;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Book{" + title +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
