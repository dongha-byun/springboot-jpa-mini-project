package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.notice.constrants.SessionConstants;
import project.notice.domain.User;
import project.notice.form.login.LoginForm;
import project.notice.form.user.UserDto;
import project.notice.service.LoginService;
import project.notice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;
import java.util.UUID;

import static project.notice.constrants.SessionConstants.*;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final String LOGIN_PAGE = "login/login";


    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm,
                            HttpServletRequest request){

        log.info("LoginController - Get loginForm : {}", request.getParameter("redirectPath"));

        return "login/login";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request){

        log.info("loginForm = {}", loginForm);

        // 필드 오류 처리
        if(bindingResult.hasErrors()){
            log.error(bindingResult.toString());
            return LOGIN_PAGE;
        }

        // 기타 복합 validation 처리
        // ex. id, password 검증 등
        // 얘를 바꿔야 로그인아이디에 대한 패스워드 실패 횟수를 처리할 수 있을거같음
        // 일단 loginid 기준으로 user를 뽑아놓고, pw를 비교해서 틀리면 조회된 user의 loginfailcnt를 증가시켜야할 것으로 보임
        User user = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(user == null){
            //bindingResult.reject("loginIdNotFound", "존재하지 않는 아이디 입니다.");
            bindingResult.reject("userNotMatched", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return LOGIN_PAGE;
        }
//        if(!user.getPassword().equals(loginForm.getPassword())){
//            bindingResult.reject("loginPasswordNotEqual", "패스워드를 다시 확인해주세요.");
//            return LOGIN_PAGE;
//        }

        UserDto userDto = new UserDto(user.getId(), user.getLoginId(), user.getName(), user.getTelNo(), user.getGrade(), user.getNickName());

        log.info("user info={}", userDto);

        // 로그인 처리
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, userDto);

        String redirectPath = request.getParameter("redirectPath");
        if(StringUtils.hasText(redirectPath)){
            return "redirect:"+redirectPath;
        }

        return "redirect:/board/list";
    }
}
