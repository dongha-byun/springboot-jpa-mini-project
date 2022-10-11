package project.notice.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.notice.authorized.AuthorizedUser;
import project.notice.domain.User;
import project.notice.form.user.UserDto;
import project.notice.form.user.UserEditForm;
import project.notice.service.UserService;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final UserService userService;

    @GetMapping
    public String viewMyPage(Model model,
                             @RequestAttribute AuthorizedUser authorizedUser){
        log.info("user : {}", authorizedUser);

        User user = userService.findOne(authorizedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 조회 실패"));

        UserDto userDto = new UserDto(user.getId(), user.getLoginId(), user.getName(),
                user.getTelNo(), user.getGrade(), user.getNickName());
        model.addAttribute("userDto", userDto);

        return "user/myPage";
    }

    @PostMapping
    public String editUserInMyPage(@ModelAttribute("editForm") UserEditForm editForm,
                                   BindingResult bindingResult,
                                   @RequestAttribute AuthorizedUser authorizedUser){
        if(bindingResult.hasErrors()){
            return "user/myPage";
        }

        userService.update(authorizedUser.getId(), editForm);

        return "redirect:/myPage";
    }
}
