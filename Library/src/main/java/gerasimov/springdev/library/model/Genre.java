package gerasimov.springdev.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    public Genre(UUID id, String name) {
        this.id = id;
        this.name = name.toLowerCase().trim();
    }

    public Genre(String name) {
        this.name = name.toLowerCase().trim();
    }

    //For JPA
    public Genre() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
