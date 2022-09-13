package project.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.*;
import project.notice.form.user.UserInsertForm;
import project.notice.repository.ArticleRepository;
import project.notice.repository.UserRepository;
import project.notice.repository.jpa.ArticleJpaRepository;
import project.notice.service.ArticleService;
import project.notice.service.BoardService;
import project.notice.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitDataService initDataService;

    @PostConstruct
    public void init(){
        initDataService.init();
    }

    @Slf4j
    @Component
    static class InitDataService{

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init(){
            User user = new User("testByun", "testByun!", "테스트동하", "010-0000-0000", "");
            em.persist(user);

            User user2 = new User("test", "test1!", "테스터", "010-0000-0000", "");
            em.persist(user2);

            User user3 = new User("byunsw4", "sorkgkf1!", "변동하", "010-0000-0000", "");
            em.persist(user3);

            Board board = new Board("테스트 게시판 1",  "게시판 설명");
            em.persist(board);
            log.info("board id : {}", board.getId());

            String title = "테스트 게시글 ";
            String content = "테스트 게시글 내용 ";

            for(int i=1; i<=30; i++){
                Article article = new Article(title + i, content + i, i, user, board);
                em.persist(article);

                File file = File.createFile("첨부파일 " + i, "png", "/path/test", article);
                em.persist(file);

                Comment comment = Comment.createComment("댓글 " + i, article, null);
                em.persist(comment);
            }
        }
    }
}