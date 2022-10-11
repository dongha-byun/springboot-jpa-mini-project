package project.notice.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.domain.Comment;
import project.notice.domain.User;
import project.notice.repository.jpa.ArticleJpaRepository;
import project.notice.repository.jpa.BoardJpaRepository;
import project.notice.repository.jpa.CommentJpaRepository;
import project.notice.repository.jpa.UserJpaRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentJpaRepositoryTest {

    @Autowired
    ArticleJpaRepository articleRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    BoardJpaRepository boardJpaRepository;
    @Autowired
    CommentJpaRepository commentJpaRepository;
    @Autowired EntityManager em;

    @Test
    void saveTest(){
        // given
        User writer = new User("testUser1", "test1!", "테스트유저", "010-1111-1111", "");
        Board board = new Board("게시판 1", "비고");
        Article article = new Article("게시글 테스트 1", "내용", 1, writer, board);
        userJpaRepository.save(writer);
        boardJpaRepository.save(board);
        articleRepository.save(article);

        Comment comment = Comment.createComment("댓글 입니다.", article, writer,null);

        // when
        Comment saveComment = commentJpaRepository.save(comment);
        em.flush();
        em.clear();

        Comment findComment = commentJpaRepository.findById(comment.getId()).get();

        // then
        assertThat(findComment).isEqualTo(saveComment);
    }

    @Test
    void findAllByArticleTest(){
        // given
        User writer = new User("testUser1", "test1!", "테스트유저", "010-1111-1111", "");
        Board board = new Board("게시판 1", "비고");
        Article article1 = new Article("게시글 테스트 1", "내용", 1, writer, board);
        Article article2 = new Article("게시글 테스트 2", "내용", 2, writer, board);
        userJpaRepository.save(writer);
        boardJpaRepository.save(board);
        articleRepository.save(article1);
        articleRepository.save(article2);

        Comment comment1 = Comment.createComment("댓글 입니다.1", article1, writer,null);
        Comment comment2 = Comment.createComment("댓글 입니다.2", article1, writer,comment1);
        Comment comment3 = Comment.createComment("댓글 입니다.3", article2, writer,null);
        Comment comment4 = Comment.createComment("댓글 입니다.4", article2, writer,comment3);

        commentJpaRepository.save(comment1);
        commentJpaRepository.save(comment2);
        commentJpaRepository.save(comment3);
        commentJpaRepository.save(comment4);

        em.flush();
        em.clear();

        // when
        List<Comment> commentList = commentJpaRepository.findAllByArticleId(article1.getId());
        List<Comment> commentList2 = commentJpaRepository.findAllByArticleId(article2.getId());

        // then
        assertThat(commentList.size()).isEqualTo(2);
        assertThat(commentList).extracting("content")
                .containsExactly("댓글 입니다.1", "댓글 입니다.2");

        assertThat(commentList2.size()).isEqualTo(2);
        assertThat(commentList2).extracting("content")
                .containsExactly("댓글 입니다.3", "댓글 입니다.4");
    }
}