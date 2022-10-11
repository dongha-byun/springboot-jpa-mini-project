package project.notice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.User;
import project.notice.form.user.UserEditForm;

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

    @Test
    @Transactional
    void 사용자_정보_수정_테스트(){
        //given
        UserEditForm editForm = new UserEditForm();
        editForm.setNickName("동하닉네임");
        editForm.setTelNo("011-1111-2222");

        //when
        userService.update(1L, editForm);

        //then
        User user = userService.findOne(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        assertThat(user.getNickName()).isEqualTo("동하닉네임");
        assertThat(user.getTelNo()).isEqualTo("011-1111-2222");
    }
}
