package gerasimov.springdev.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    private final UUID id;
    private String name;

    public Genre(UUID id, String name) {
        this.id = id;
        this.name = name.toLowerCase().trim();
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
}
