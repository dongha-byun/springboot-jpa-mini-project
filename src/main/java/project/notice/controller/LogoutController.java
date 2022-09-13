package project.notice.controller;

import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        // 세션 생성은 안하고 있는거 조회만, 없으면 null 반환
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        log.info("logout controller in");

        return "redirect:/";
    }
}
