package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.domain.User;
import project.notice.form.change.ChangePwForm;
import project.notice.service.UserService;

@Controller
@Slf4j
@RequestMapping("/change/pw")
@RequiredArgsConstructor
public class ChangePwController {

    private final UserService userService;

    private static final String CHANGE_PW_FORM_PAGE = "change/changePwForm";

    @GetMapping
    public String changePwForm(@ModelAttribute("changePwForm") ChangePwForm changePwForm){
        return "change/changePwForm";
    }

    @PostMapping
    public String changePw(@Validated @ModelAttribute("changePwForm") ChangePwForm changePwForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        log.info("changePwForm : {}", changePwForm);

        if(bindingResult.hasErrors()){
            return CHANGE_PW_FORM_PAGE;
        }

        if(!changePwForm.isEqualOrgPassword()){
            bindingResult.reject("notEqualOrgPassword","기존 비밀번호가 일치하지 않습니다.");
            return CHANGE_PW_FORM_PAGE;
        }

        if(!changePwForm.isEqualChgPassword()){
            bindingResult.reject("notEqualChgPassword","비밀번호가 일치하지 않습니다.");
            return CHANGE_PW_FORM_PAGE;
        }

        userService.changeUserPassword(changePwForm);

        redirectAttributes.addAttribute("changePwSuccess", true);

        return "redirect:/login";
    }
}
