package project.notice.repository;

import project.notice.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();
}
