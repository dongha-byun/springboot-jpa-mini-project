package project.notice.repository;

import project.notice.domain.Article;
import project.notice.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findAll();
    List<Comment> findAllByArticleId(Long articleId);

    void flushAndClear();
}
