package gerasimov.springdev.nosqllibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document
public class Book {
    @Id
    private String id;

    private String title;
    private Set<String> commentIds = new HashSet<>();
    private List<Genre> genres;
    private Set<String> authorIds;

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
        commentIds.add(commentId);
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
