package project.notice.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.repository.CommentRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository implements CommentRepository {

    private final EntityManager em;

    public Comment save(Comment comment){
        em.persist(comment);
        return comment;
    }

    public Optional<Comment> findById(Long id){
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public List<Comment> findAllByArticleId(Long articleId) {
        return em.createQuery("select c from Comment c join fetch c.article a " +
                        "where 1=1 and a.id = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }

}
