package project.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.notice.repository.ArticleRepository;
import project.notice.repository.BoardRepository;
import project.notice.repository.UserRepository;
import project.notice.repository.jpa.ArticleJpaRepository;
import project.notice.repository.jpa.BoardJpaRepository;
import project.notice.repository.jpa.UserJpaRepository;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private final EntityManager articleEm;
    private final EntityManager userEm;

    private final EntityManager boardEm;

    @Autowired
    public SpringConfig(EntityManager articleEm, EntityManager userEm, EntityManager boardEm) {
        this.articleEm = articleEm;
        this.userEm = userEm;
        this.boardEm = boardEm;
    }

    @Bean
    public UserRepository userRepository(){
        return new UserJpaRepository(userEm);
    }

    @Bean
    public ArticleRepository articleRepository(){
        return new ArticleJpaRepository(articleEm);
    }

    @Bean
    public BoardRepository boardRepository(){
        return new BoardJpaRepository(boardEm);
    }
}
