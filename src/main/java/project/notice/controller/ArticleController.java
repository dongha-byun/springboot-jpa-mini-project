package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.constrants.SessionConstants;
import project.notice.domain.*;
import project.notice.form.article.ArticleWriteForm;
import project.notice.form.comment.CommentSaveForm;
import project.notice.form.file.FileSaveForm;
import project.notice.form.user.UserDto;
import project.notice.manager.FileEncryptManager;
import project.notice.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/article/write")
    public String articleWritePage(@ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                                   Model model){

        List<Board> boardList = boardService.findBoardAll();
        model.addAttribute("boardList", boardList);

        return "article/articleWrite";
    }

    @PostMapping("/article/write")
    public String articleWrite(@Validated  @ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                               BindingResult bindingResult,
                               @RequestParam MultipartFile file,
                               HttpServletRequest request) throws IOException{
        if(bindingResult.hasErrors()){
            return "article/articleWrite";
        }

        HttpSession session = request.getSession();
        log.info("session : {}",session);

        UserDto userDto = (UserDto) session.getAttribute(SessionConstants.LOGIN_USER);
        if(userDto == null){
            bindingResult.reject("sessionExpire", "세션 정보가 유효하지 않습니다. 다시 시도해주세요.");
            return "article/articleWrite";
        }

        Board board = boardService.findOne(articleWriteForm.getBoardId());
        if(board == null){
            bindingResult.reject("noAuthBoard", "작성 권한이 없는 게시판에 작성이 불가합니다.");
            return "article/articleWrite";
        }

        log.info("=====================");
        log.info("request : {}", request);
        log.info("file : {}", file);
        log.info("=====================");

        User user = userService.getUser(userDto);
        Article article = articleService.saveArticle(articleWriteForm, user, board);

        // 파일저장
        fileService.saveFile(file, article);

        return "redirect:/board/list";
    }

    @GetMapping("/article/{id}")
    public String articleView(@PathVariable("id") Long id,
                              Model model){
        Article article = articleService.readArticle(id);
        if(article == null){
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }


        List<Comment> commentList = article.getCommentList();
        List<AttachFile> fileList = article.getAttachFileList();

        model.addAttribute("article", article);
        model.addAttribute("commentList", commentList);
        model.addAttribute("fileList", fileList);
        model.addAttribute("commentSaveForm", new CommentSaveForm());

        return "article/articleView";

    }

    @GetMapping("/article/{id}/edit")
    public String editArticlePage(@PathVariable("id") Long id,
                                  @ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                                  Model model){

        log.info("=== go article edit page == id : {}", id);

        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);

        articleWriteForm = ArticleWriteForm.convertEntityToForm(article);

        return "article/articleEdit";
    }

    @PostMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes){
        log.info("=== article delete ===");

        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("isSuccessDelete", true);

        return "redirect:/board/list";
    }



}
