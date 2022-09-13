package project.notice.repository.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.notice.domain.Board;
import project.notice.repository.BoardRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class BoardJpaRepository implements BoardRepository {

    private final EntityManager em;

    public Board save(Board board){
        em.persist(board);
        log.info("board persist");
        return board;
    }

    public Optional<Board> findById(Long id){
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public List<Board> findAll(){
        return em.createQuery("select board from Board board", Board.class)
                .getResultList();
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
