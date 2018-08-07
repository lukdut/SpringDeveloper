package gerasimov.springdev.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @ManyToMany
    private List<Author> authors;
    @ManyToMany
    private List<Genre> genres;

    //For JPA
    public Book() {
    }

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title.trim().toLowerCase();
        this.authors = authors;
        this.genres = genres;
    }

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
