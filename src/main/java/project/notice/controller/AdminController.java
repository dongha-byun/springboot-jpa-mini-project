package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.notice.domain.Grade;
import project.notice.dto.user.UserDto;
import project.notice.service.AdminUserService;
import project.notice.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminUserService adminUserService;

    @ModelAttribute("grades")
    public Grade[] grades(){
        return Grade.values();
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin/adminMain";
    }

    @GetMapping("/admin/user")
    public String adminUserContent(Model model){
        List<UserDto> allUserDto = userService.findAllUserDto();
        model.addAttribute("allUserDto", allUserDto);
        return "admin/adminUser";
    }

    @GetMapping("/admin/board")
    public String adminBoardContent(){
        return "admin/adminBoard";
    }

    @PostMapping("/admin/user/{userId}/grade")
    public String editUserGradeByAdmin(@PathVariable("userId") Long id,
                                       @RequestParam Map<String ,String> param){
        log.info("param : {}", param);

        adminUserService.updateUserGrade(id, Grade.valueOf(param.get("grade")));

        return "redirect:/admin";
    }
}
