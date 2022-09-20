package project.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.notice.interceptor.SessionCheckInterceptor;
import project.notice.repository.ArticleRepository;
import project.notice.repository.BoardRepository;
import project.notice.repository.FileRepository;
import project.notice.repository.UserRepository;
import project.notice.repository.jpa.ArticleJpaRepository;
import project.notice.repository.jpa.BoardJpaRepository;
import project.notice.repository.jpa.FileJpaRepository;
import project.notice.repository.jpa.UserJpaRepository;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private final EntityManager articleEm;
    private final EntityManager userEm;

    private final EntityManager boardEm;

    private final EntityManager fileEm;

    @Autowired
    public SpringConfig(EntityManager articleEm, EntityManager userEm, EntityManager boardEm, EntityManager fileEm) {
        this.articleEm = articleEm;
        this.userEm = userEm;
        this.boardEm = boardEm;
        this.fileEm = fileEm;
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

    @Bean
    public FileRepository fileRepository(){
        return new FileJpaRepository(fileEm);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/logout","/board/list/**",
                        "/css/**", "/*.ico","/js/**","/webjars/**",
                        "/find/**", "/error");
    }
}
