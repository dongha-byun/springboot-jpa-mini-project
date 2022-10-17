package project.notice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AdminController {

    @GetMapping("/admin")
    public String adminPage(){
        return "admin/adminMain";
    }

    @GetMapping("/admin/user")
    public String adminUserContent(){
        return "admin/adminUser";
    }

    @GetMapping("/admin/board")
    public String adminBoardContent(){
        return "admin/adminBoard";
    }
}
