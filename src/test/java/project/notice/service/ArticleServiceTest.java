package project.notice.service;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.domain.Comment;
import project.notice.domain.User;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ArticleService articleService;

    @Test
    void 게시글_저장_테스트_댓글_게시판_까지(){
        User user = new User("testcase1", "test1!", "테스트1", "010-0000-0000", "");
        Board board = new Board("테스트케이스게시판", "");
        Article article = new Article("테스트", "내용", 1, user, board);

        em.persist(user);
        em.persist(board);
        em.persist(article);


        Comment comment = Comment.createComment("댓글 1", article, null);
        em.persist(comment);

        em.flush();
        em.clear();

        Article findArticle = em.find(Article.class, article.getId());
        List<Comment> commentList = findArticle.getCommentList();

        assertThat(commentList.size()).isEqualTo(1);
    }


    @Test
    void 게시글_삭제(){
        // given
        User user = new User("testcase1", "test1!", "테스트1", "010-0000-0000", "");
        Board board = new Board("테스트케이스게시판", "");
        Article article = new Article("테스트", "내용", 1, user, board);

        em.persist(user);
        em.persist(board);
        em.persist(article);

        // when
        articleService.deleteArticle(article.getId());

        em.flush();
        em.clear();

        // then
        Article delArticle = articleService.getArticle(article.getId());
        assertThat(delArticle).isNull();

    }
}