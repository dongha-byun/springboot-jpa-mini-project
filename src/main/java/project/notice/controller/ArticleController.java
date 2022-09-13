package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.domain.File;
import project.notice.form.article.ArticleWriteForm;
import project.notice.service.ArticleService;
import project.notice.service.CommentService;
import project.notice.service.FileService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    @GetMapping("/article/write")
    public String articleWritePage(@ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm){
        return "article/articleWrite";
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
