package project.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.notice.domain.User;
import project.notice.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String loginId, String password){
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(
                        () -> new IllegalArgumentException("사용자 조회 실패")
                );
        if(!password.equals(user.getPassword())){
            user.increaseLoginFailCnt();
            return null;
        }
        return user;
    }
}
