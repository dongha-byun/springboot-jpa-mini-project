package project.notice.repository.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.notice.domain.Article;
import project.notice.repository.ArticleRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ArticleJpaRepository implements ArticleRepository {

    private final EntityManager em;

    public Article save(Article article){
        log.info("save in article : {}", article);
        em.persist(article);
        return article;
    }

    public Optional<Article> findById(Long id){
        return Optional.ofNullable(em.find(Article.class, id));
    }

    public List<Article> findAll(){
        return em.createQuery("select article from Article article", Article.class)
                .getResultList();
    }

    public Optional<Article> findByIdWithFetchJoin(Long id){
         return  em.createQuery(
                        "select a from Article a " +
                                "join fetch a.board b " +
                                "join fetch a.noticeUser u " +
                                "where 1=1 " +
                                "and a.id = :id", Article.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }
}
