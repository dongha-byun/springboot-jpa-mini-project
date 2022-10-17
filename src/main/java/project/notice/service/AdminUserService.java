package project.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Grade;
import project.notice.domain.User;
import project.notice.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserGrade(Long id, Grade grade){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 조회 실패"));

        user.updateGradeByAdmin(grade);
    }
}
