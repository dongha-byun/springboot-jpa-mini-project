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
        return userRepository.findByLoginId(loginId)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }
}
