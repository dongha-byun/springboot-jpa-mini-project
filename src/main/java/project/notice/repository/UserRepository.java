package project.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.notice.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByLoginId(String loginId);

    Optional<User> findLoginId(String name, String telNo);

    Optional<User> findPassword(String loginId, String name, String telNo);
}
