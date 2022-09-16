package project.notice.manager;

import org.springframework.stereotype.Component;
import project.notice.domain.Board;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ArticleNoCreateManager {
    private final static Map<Long, Integer> articleNoManager = new ConcurrentHashMap<>();

    // 인자로 받은 게시판의 가장 높은 게시글 번호 정보 조회
    public Integer getMaxArticleNo(Board board){
        Integer maxNo = articleNoManager.get(board.getId());
        if(maxNo != null){
            articleNoManager.put(board.getId(), ++maxNo);
            return maxNo;
        }else{
            articleNoManager.put(board.getId(), 1);
            return 1;
        }
    }
}
