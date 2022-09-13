package project.notice.repository.jpa;

import project.notice.domain.Article;
import project.notice.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findAll();
}
