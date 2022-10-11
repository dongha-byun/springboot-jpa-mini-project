package project.notice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.notice.domain.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void 사용자_정보_조회_테스트(){
        User user = userService.findOne(1L)
                .orElseThrow(() -> new IllegalArgumentException("사용자 조회 실패"));

        assertThat(user.getName()).isEqualTo("테스트동하");
        assertThat(user.getLoginId()).isEqualTo("testByun");
        assertThat(user.getNickName()).isEqualTo("");
        assertThat(user.getPassword()).isEqualTo("testByun!");
    }
}
