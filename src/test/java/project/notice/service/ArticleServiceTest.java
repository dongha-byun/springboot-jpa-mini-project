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

    User user;
    Board board;
    Article article;

    @BeforeEach
    void beforeEach(){
        user = new User("testcase1", "test1!", "테스트1", "010-0000-0000", "");
        board = new Board("테스트케이스게시판", "");
        article = new Article("테스트", "내용", 1, user, board);

        em.persist(user);
        em.persist(board);
        em.persist(article);
    }

    @Test
    void 게시글_저장_테스트_댓글_게시판_까지(){
        Comment comment = Comment.createComment("댓글 1", article, user,null);
        em.persist(comment);

        em.flush();
        em.clear();

        Article findArticle = em.find(Article.class, article.getId());
        List<Comment> commentList = findArticle.getCommentList();

        assertThat(commentList.size()).isEqualTo(1);
    }


    @Test
    void 게시글_삭제(){
        // given - beforeEach 처리

        // when
        articleService.deleteArticle(article.getId());

        em.flush();
        em.clear();

        // then
        Article delArticle = articleService.getArticle(article.getId());
        assertThat(delArticle).isNull();

    }

    @Test
    void 게시글_수정페이지_조회(){
        // read cnt 올라가면 안됨
        // given - beforeEach 처리

        //when
        Article findArticle = articleService.getArticle(article.getId());

        //then
        assertThat(findArticle.getReadCnt()).isEqualTo(0);
    }

    @Test
    void 게시글_조회(){
        // read cnt 증가해야함
        // given - beforeEach 처리

        //when
        Article findArticle = articleService.readArticle(article.getId());

        //then
        assertThat(findArticle.getReadCnt()).isEqualTo(1);
    }

    @Test
    void 게시글_저장_닉네임으로(){

        Article articleByNickName = new Article("테스트", "내용", 1, user, board, "Y");
        em.persist(articleByNickName);

        Article findArticleByNickName = em.find(Article.class, articleByNickName.getId());

        assertThat(findArticleByNickName.getNickNameYn()).isEqualTo("Y");
    }
}