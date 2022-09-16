package project.notice.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Board;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleNoCreateManagerTest {

    @Autowired ArticleNoCreateManager manager = new ArticleNoCreateManager();
    @Autowired  EntityManager em;

    @Test
    void 글번호_채번_최초(){
        //given
        Board board = new Board("테스트게시판1", "");
        em.persist(board);

        //when
        Integer maxArticleNo = manager.getMaxArticleNo(board);

        //then
        assertThat(maxArticleNo).isEqualTo(1);
    }

    @Test
    void 글번호_채번(){
        // given
        Board board = new Board("테스트게시판1", "");
        em.persist(board);

        // when
        Integer firstArticleNo = manager.getMaxArticleNo(board);
        Integer secondArticleNo = manager.getMaxArticleNo(board);

        // then
        assertThat(firstArticleNo).isEqualTo(1);
        assertThat(secondArticleNo).isEqualTo(2);
    }
}