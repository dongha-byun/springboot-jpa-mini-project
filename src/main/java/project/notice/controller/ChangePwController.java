package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.authorized.AuthorizedUser;
import project.notice.domain.User;
import project.notice.form.change.ChangePwForm;
import project.notice.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChangePwController {

    private final UserService userService;

    private static final String CHANGE_PW_FORM_PAGE = "change/changePwForm";

    @GetMapping("/change/pw")
    public String changePwFormNoSession(@ModelAttribute("changePwForm") ChangePwForm changePwForm){
        return CHANGE_PW_FORM_PAGE;
    }

    @PostMapping("/change/pw")
    public String changePwNoSession(@Validated @ModelAttribute("changePwForm") ChangePwForm changePwForm,
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

    @GetMapping("/changePassword")
    public String viewChangePasswordBySession(@ModelAttribute("changePwForm") ChangePwForm changePwForm,
                                              @RequestAttribute AuthorizedUser authorizedUser){
        User user = userService.findOne(authorizedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 조회 실패"));

        changePwForm.setTargetUserId(user.getId());
        changePwForm.setChkOrgPassword(user.getPassword());

        return CHANGE_PW_FORM_PAGE;
    }

    @PostMapping("/changePassword")
    public String changePasswordBySession(@ModelAttribute("changePwForm") ChangePwForm changePwForm,
                                          BindingResult bindingResult,
                                          @RequestAttribute AuthorizedUser authorizedUser){

        if(bindingResult.hasErrors()){
            return CHANGE_PW_FORM_PAGE;
        }

        if(!changePwForm.getTargetUserId().equals(authorizedUser.getId())){
            bindingResult.reject("NoAuthorizedUser", "사용자 정보가 만료되었습니다.");
            return CHANGE_PW_FORM_PAGE;
        }

        userService.changeUserPassword(changePwForm);

        return "redirect:/";
    }

}
