package gerasimov.springdev.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private final UUID id;
    private final String title;
    @OneToMany
    private final List<Author> authors;
    @OneToMany
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
