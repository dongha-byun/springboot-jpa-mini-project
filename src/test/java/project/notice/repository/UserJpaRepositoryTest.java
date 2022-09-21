package project.notice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.User;
import project.notice.repository.jpa.UserJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userRepository;

    @Test
    void saveTest(){
        // given
        User user = new User("test1", "test1!", "테스트유저1", "010-2222-2222", "");

        // when
        User saveUser = userRepository.save(user);
        userRepository.flushAndClear();

        User findUser = userRepository.findById(user.getId()).get();

        // then
        assertThat(findUser).isEqualTo(saveUser);
    }

    @Test
    void findAllTest(){
        // given
        User user1 = new User("user1", "user1!", "테스트유저1", "010-2222-2222", "");
        User user2 = new User("user2", "user2!", "테스트유저1", "010-2222-2222", "");
        userRepository.save(user1);
        userRepository.save(user2);

        userRepository.flushAndClear();

        // when
        List<User> result = userRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("loginId")
                .containsExactly("user1", "user2");
    }

}