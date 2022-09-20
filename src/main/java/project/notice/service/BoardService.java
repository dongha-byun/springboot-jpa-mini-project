package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Board;
import project.notice.repository.BoardRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void saveBoard(Board board){
        boardRepository.save(board);
    }

    public List<Board> findBoardAll(){
        return boardRepository.findAll();
    }

    public Board findOne(Long id){
        return boardRepository.findById(id)
                .orElse(null);
    }
}
