package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.form.user.UserInsertForm;
import project.notice.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("userForm") UserInsertForm userInsertForm){

        return "user/addForm.html";
    }

    @PostMapping("/add")
    public String addUser(@Validated @ModelAttribute("userForm") UserInsertForm userInsertForm,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        log.info("userForm = {}",userInsertForm);

        // 비밀번호 == 비밀번호 확인
        if(StringUtils.hasText(userInsertForm.getPassword())
                && StringUtils.hasText(userInsertForm.getConfirmPassword())
                && !(userInsertForm.getPassword().equals(userInsertForm.getConfirmPassword())) ){
            bindingResult.reject("passwordNotEqual", "패스워드가 일치하지 않습니다.");
        }

        // 로그인아이디 중복 체크
        if(StringUtils.hasText(userInsertForm.getLoginId())
                && userService.isDuplicateLoginId(userInsertForm.getLoginId()) ){
            bindingResult.reject("duplicateLoginId", "로그인 아이디 중복입니다.");
        }

        // 연락처 유효성 검사 (js?)

        if(bindingResult.hasErrors()){
            log.info("validation error!");
            log.info("binding result = {}", bindingResult);
            return "user/addForm";
        }

        // 가입 승인 처리
        userService.addUser(userInsertForm);

        redirectAttributes.addAttribute("status", "success");
        redirectAttributes.addAttribute("message", "가입이 성공적으로 처리되었습니다.");

        return "redirect:/login";
    }

}
