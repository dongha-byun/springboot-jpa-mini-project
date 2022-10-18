package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.domain.Board;
import project.notice.domain.Grade;
import project.notice.dto.board.BoardDto;
import project.notice.dto.user.UserDto;
import project.notice.form.board.BoardEditForm;
import project.notice.service.AdminUserService;
import project.notice.service.BoardService;
import project.notice.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminUserService adminUserService;
    private final BoardService boardService;

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
    public String adminBoardContent(Model model){
        List<BoardDto> allBoardDto = boardService.findBoardAll()
                .stream().map(board -> new BoardDto(board.getId(), board.getBoardName(), board.getDescription()))
                .collect(Collectors.toList());
        model.addAttribute("allBoardDto", allBoardDto);
        return "admin/adminBoard";
    }

    @GetMapping("/admin/board/{id}")
    public String adminBoardDetail(@PathVariable("id") Long id,
                                   Model model){
        Board board = boardService.findOne(id);
        model.addAttribute("boardDto", new BoardDto(board.getId(), board.getBoardName(), board.getDescription()));

        return "admin/board/adminBoardView";
    }

    @GetMapping("/admin/board/{id}/edit")
    public String adminBoardEditForm(@PathVariable("id") Long id,
                                     Model model){
        Board board = boardService.findOne(id);
        BoardEditForm boardEditForm = new BoardEditForm(board.getId(),board.getBoardName(), board.getDescription());

        model.addAttribute("boardEditForm", boardEditForm);

        return "admin/board/adminBoardEdit";
    }

    @PostMapping("/admin/user/{userId}/grade")
    public String editUserGrade(@PathVariable("userId") Long id,
                                @RequestParam Map<String ,String> param){
        log.info("param : {}", param);

        adminUserService.updateUserGrade(id, Grade.valueOf(param.get("grade")));

        return "redirect:/admin";
    }

    @PostMapping("/admin/board/{id}/edit")
    public String editBoardInfo(@PathVariable("id") Long id,
                                @ModelAttribute BoardEditForm editForm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/board/adminBoardEdit";
        }

        boardService.updateBoard(id, editForm);

        redirectAttributes.addFlashAttribute("id", id);

        return "redirect:/admin/board/{id}";
    }
}
