package gerasimov.springdev.nosqllibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Book {
    @Id
    private String id;

    private String title;
    private List<String> comments = new ArrayList<>();
    private Set<String> genresIds = new HashSet<>();
    private Set<String> authorIds = new HashSet<>();

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<String> authorIds) {
        this.authorIds = authorIds;
    }

    public void addComment(String commentId) {
        comments.add(commentId);
    }

    public List<String> getComments() {
        return comments;
    }

    public Set<String> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(Set<String> genresIds) {
        this.genresIds = genresIds;
    }

    @Override
    public String toString() {
        return title + " (" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }
}
