package project.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.domain.User;
import project.notice.form.article.ArticleWriteForm;
import project.notice.manager.ArticleNoCreateManager;
import project.notice.repository.ArticleRepository;
import project.notice.repository.jpa.ArticleJpaRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleNoCreateManager articleNoCreateManager;

    public List<Article> articleListAll(){
        return articleRepository.findAll();
    }

    @Transactional
    public Article saveArticle(ArticleWriteForm articleWriteForm, User user, Board board){
        Article article = writeFormToArticle(articleWriteForm, user, board);
        saveArticle(article);
        return article;
    }

    @Transactional
    public void saveArticle(Article article){
        articleRepository.save(article);
    }

    @Transactional
    public Article readArticle(Long id){
        Article article = getArticle(id);
        if(article!=null){
            article.incrementReadCount();
        }
        return article;
    }

    private Article writeFormToArticle(ArticleWriteForm articleWriteForm, User user, Board board) {
        Integer articleNo = articleNoCreateManager.getMaxArticleNo(board);

        Article article = new Article(articleWriteForm.getTitle(), articleWriteForm.getContent(), articleNo, user, board);
        return article;
    }

    public Article getArticle(Long id) {
        return articleRepository.findByIdWithFetchJoin(id)
                .orElse(null);
    }

    @Transactional
    public void deleteArticle(Long id){
        Article article = getArticle(id);
        if(article != null){
            articleRepository.delete(article);
        }
    }
}
