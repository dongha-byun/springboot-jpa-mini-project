package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.domain.User;
import project.notice.form.article.ArticleWriteForm;
import project.notice.repository.ArticleRepository;
import project.notice.repository.jpa.ArticleJpaRepository;

import javax.servlet.http.HttpSession;
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
    public void saveArticle(ArticleWriteForm articleWriteForm, User user, Board board){
        Article article = new Article(articleWriteForm.getTitle(), articleWriteForm.getContent(), user, board);
        articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        return articleRepository.findByIdWithFetchJoin(id)
                .orElse(null);
    }
}
