package gerasimov.springdev.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private UUID id;
    private String text;

    //For JPA
    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
