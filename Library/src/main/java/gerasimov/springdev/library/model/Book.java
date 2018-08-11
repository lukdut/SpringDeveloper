package gerasimov.springdev.library.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Author> authors;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Genre> genres;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;

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
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                ", comments=" + comments +
                '}';
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
}
