package project.notice.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Board;
import project.notice.repository.jpa.BoardJpaRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardJpaRepositoryTest {

    @Autowired
    BoardJpaRepository boardJpaRepository;

    @Test
    void saveTest(){
        // given
        Board board = new Board("게시판1", "비고");

        // when
        Board saveBoard = boardJpaRepository.save(board);
        boardJpaRepository.flushAndClear();

        Board findBoard = boardJpaRepository.findById(board.getId()).get();

        // then
        assertThat(findBoard.getBoardName()).isEqualTo("게시판1");
        assertThat(findBoard).isEqualTo(saveBoard);
    }
}