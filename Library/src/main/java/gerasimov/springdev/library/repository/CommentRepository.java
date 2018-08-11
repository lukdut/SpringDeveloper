package gerasimov.springdev.library.repository;

import gerasimov.springdev.library.model.Comment;
import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface CommentRepository extends Repository<Comment, UUID> {
    void save(Comment comment);
}
