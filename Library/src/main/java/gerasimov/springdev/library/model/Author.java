package gerasimov.springdev.library.model;


import java.util.UUID;

public class Author {
    private final UUID id;
    private String fullName;

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
