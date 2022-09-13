package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.repository.ArticleRepository;
import project.notice.repository.jpa.ArticleJpaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> articleListAll(){
        return articleRepository.findAll();
    }

    @Transactional
    public void saveArticle(Article article){
        articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        return articleRepository.findByIdWithFetchJoin(id)
                .orElse(null);
    }
}
