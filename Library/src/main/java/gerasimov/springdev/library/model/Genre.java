package gerasimov.springdev.library.model;

import java.util.UUID;

public class Genre {
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
