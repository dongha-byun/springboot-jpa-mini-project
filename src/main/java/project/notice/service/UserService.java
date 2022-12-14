package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.User;
import project.notice.form.change.ChangePwForm;
import project.notice.form.find.FindIdForm;
import project.notice.form.find.FindPwForm;
import project.notice.dto.user.UserDto;
import project.notice.form.user.UserEditForm;
import project.notice.form.user.UserInsertForm;
import project.notice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(UserDto userDto){
        return userRepository.findById(userDto.getId())
                .orElseGet(() -> null);
    }

    public Optional<User> findOne(Long id){
        return userRepository.findById(id);
    }

    public boolean isDuplicateLoginId(String loginId){
        User user = userRepository.findByLoginId(loginId)
                .orElseGet(() -> null);

        return user != null;
    }

    @Transactional
    public void addUser(UserInsertForm form){
        User user = new User(form.getLoginId(), form.getPassword(), form.getName(), form.getTelNo(), form.getNickName());
        userRepository.save(user);
    }

    public User findLoginId(FindIdForm findIdForm){
        return userRepository.findLoginId(findIdForm.getName(), findIdForm.getTelNo())
                .orElse(null);
    }

    public User findPassword(FindPwForm findPwForm){
        return userRepository.findPassword(findPwForm.getLoginId(), findPwForm.getName(), findPwForm.getTelNo())
                .orElse(null);
    }

    @Transactional
    public void changeUserPassword(ChangePwForm changePwForm){
        User user = userRepository.findById(changePwForm.getTargetUserId())
                .orElse(null);
        if(user != null){
            user.changePassword(changePwForm.getChgPassword());
        }
    }

    @Transactional
    public void update(long l, UserEditForm editForm) {
        User user = userRepository.findById(l)
                .orElseThrow();

        user.editByMyPage(editForm);
    }

    public List<UserDto> findAllUserDto(){
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getLoginId(), user.getName(), user.getTelNo(), user.getGrade(), user.getNickName()))
                .collect(Collectors.toList());
    }
}
