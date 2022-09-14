package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.notice.constrants.SessionConstants;
import project.notice.domain.*;
import project.notice.form.article.ArticleWriteForm;
import project.notice.service.ArticleService;
import project.notice.service.BoardService;
import project.notice.service.CommentService;
import project.notice.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;

    @GetMapping("/article/write")
    public String articleWritePage(@ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm){
        return "article/articleWrite";
    }

    @PostMapping("/article/write")
    public String articleWrite(@Validated  @ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                               BindingResult bindingResult,
                               HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "article/articleWrite";
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionConstants.LOGIN_USER);
        if(user == null){
            bindingResult.reject("sessionExpire", "세션 정보가 유효하지 않습니다. 다시 시도해주세요.");
            return "article/articleWrite";
        }

        Board board = boardService.findOne(articleWriteForm.getBoardId());
        if(board == null){
            bindingResult.reject("noAuthBoard", "작성 권한이 없는 게시판에 작성이 불가합니다.");
            return "article/articleWrite";
        }

        articleService.saveArticle(articleWriteForm, user, board);

        return "redirect:/board/list";
    }

    @GetMapping("/article/{id}")
    public String articleView(@PathVariable("id") Long id,
                              Model model){
        Article article = articleService.getArticle(id);
        if(article == null){
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }

        List<Comment> commentList = article.getCommentList();
        List<File> fileList = article.getAttachFileList();

        model.addAttribute("article", article);
        model.addAttribute("commentList", commentList);
        model.addAttribute("fileList", fileList);

        return "article/articleView";

    }

    @GetMapping("/article/{id}/edit")
    public String editArticlePage(@PathVariable("id") Long id,
                              Model model){

        log.info("=== go article edit page == id : {}", id);
        return "article/articleEdit";
    }

    @PostMapping("/article/{id}/del")
    public String deleteArticle(@PathVariable("id") Long id){
        log.info("=== article delete ===");
        return "redirect:/board/list";
    }



}
