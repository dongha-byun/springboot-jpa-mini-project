package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.domain.User;
import project.notice.form.change.ChangePwForm;
import project.notice.form.find.FindIdForm;
import project.notice.form.find.FindPwForm;
import project.notice.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
@RequestMapping("/find")
@RequiredArgsConstructor
public class FindUserInfoController {

    private final UserService userService;
    private static final String FIND_ID_FORM_PAGE="find/findIdForm";
    private static final String FIND_PW_FORM_PAGE="find/findPwForm";

    @GetMapping("/id")
    public String findLoginIdForm(@ModelAttribute("findIdForm") FindIdForm findIdForm ){
        return "find/findIdForm";
    }

    @PostMapping("/id")
    public String findLoginId(@Validated @ModelAttribute("findIdForm") FindIdForm findIdForm,
                              BindingResult bindingResult,
                              Model model){

        if(bindingResult.hasErrors()){
            return FIND_ID_FORM_PAGE;
        }

        User findUser = userService.findLoginId(findIdForm);
        if(findUser == null){
            bindingResult.reject("userNotFound", "일치하는 사용자 정보가 존재하지 않습니다.");
            return FIND_ID_FORM_PAGE;
        }
        String loginId = findUser.getLoginId();
        String subStr = loginId.substring(loginId.length()-3);
        String masking = StringUtils.replace(loginId, subStr, "***");

        model.addAttribute("findLoginId", masking);
        model.addAttribute("username", findUser.getName());

        log.info("findUser loginId : {}", loginId);
        log.info("subStr : {}", subStr);
        log.info("masking : {}", masking);

        return "find/findIdResult"; // 찾기 결과 페이지
    }

    @GetMapping("/pw")
    public String findPasswordForm(@ModelAttribute("findPwForm")FindPwForm findPwForm){
        return "find/findPwForm";
    }

    @PostMapping("/pw")
    public String findPassword(@Validated @ModelAttribute("findPwForm")FindPwForm findPwForm,
                               BindingResult bindingResult,
                               Model model){

        if(bindingResult.hasErrors()){
            return FIND_PW_FORM_PAGE;
        }

        User findUser = userService.findPassword(findPwForm);
        if(findUser == null){
            bindingResult.reject("userNotFound", "일치하는 사용자 정보가 존재하지 않습니다.");
            return FIND_PW_FORM_PAGE;
        }

        ChangePwForm changePwForm = ChangePwForm.createFormWithChkPw(findUser.getPassword(), findUser.getId());
        model.addAttribute("changePwForm", changePwForm);

        // 패스워드 재설정 처리
        return "change/changePwForm";
    }
}
