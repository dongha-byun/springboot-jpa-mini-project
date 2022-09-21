package project.notice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.domain.User;
import project.notice.repository.jpa.ArticleJpaRepository;
import project.notice.repository.jpa.BoardJpaRepository;
import project.notice.repository.jpa.UserJpaRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleJpaRepositoryTest {

    @Autowired
    ArticleJpaRepository articleRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    BoardJpaRepository boardJpaRepository;
    @Autowired EntityManager em;

    @Test
    void saveTest(){
        // given
        User writer = new User("testUser1", "test1!", "테스트유저", "010-1111-1111", "");
        Board board = new Board("게시판 1", "비고");
        userJpaRepository.save(writer);
        boardJpaRepository.save(board);

        Article article = new Article("게시글 테스트 1", "내용", 1, writer, board);

        // when
        Article saveArticle = articleRepository.save(article);
        em.flush();
        em.clear();

        Article findArticle = articleRepository.findById(article.getId()).get();

        // then
        assertThat(findArticle).isEqualTo(saveArticle);
    }

    @Test
    void findAllTest(){
        // given
        User writer = new User("testUser1", "test1!", "테스트유저", "010-1111-1111", "");
        Board board = new Board("게시판 1", "비고");
        userJpaRepository.save(writer);
        boardJpaRepository.save(board);

        Article article1 = new Article("게시글 테스트 1", "내용", 1, writer, board);
        Article article2 = new Article("게시글 테스트 2", "내용2", 2, writer, board);
        articleRepository.save(article1);
        articleRepository.save(article2);

        em.flush();
        em.clear();

        // when
        List<Article> result = articleRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("title")
                .containsExactly("게시글 테스트 1", "게시글 테스트 2");
    }
}