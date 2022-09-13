package project.notice.repository;

import project.notice.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
    Optional<Article> findByIdWithFetchJoin(Long id);
}
