package gerasimov.springdev.library.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String fullName;

    //For JPA
    public Author() {
    }

    public Author(String fullName) {
        this.fullName = fullName.trim().toLowerCase();
    }

    public Author(UUID id, String fullName) {
        this.id = id;
        this.fullName = fullName.trim().toLowerCase();
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(fullName, author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}
