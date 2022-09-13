package project.notice.repository.jpa;

import lombok.RequiredArgsConstructor;
import project.notice.domain.User;
import project.notice.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository {
    private final EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll(){
        return em.createQuery("select user from User user", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findByLoginId(String loginId){
        return findAll().stream()
                .filter(u -> u.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public Optional<User> findLoginId(String name, String telNo) {
        return em.createQuery("select user from User user " +
                        "where 1=1 " +
                        "and user.name=:name " +
                        "and user.telNo=:telNo", User.class)
                .setParameter("name", name)
                .setParameter("telNo", telNo)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public Optional<User> findPassword(String loginId, String name, String telNo) {
        return findLoginId(name, telNo)
                .filter(user -> user.getLoginId().equals(loginId));
    }


    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
