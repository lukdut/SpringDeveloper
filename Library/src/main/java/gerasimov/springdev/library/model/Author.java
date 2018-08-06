package gerasimov.springdev.library.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String fullName;

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
}
