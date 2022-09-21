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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.form.comment.CommentSaveForm;
import project.notice.service.ArticleService;
import project.notice.service.CommentService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/comment/add/{id}")
    public String addComment(@Validated @ModelAttribute("commentSaveForm") CommentSaveForm commentSaveForm,
                             BindingResult bindingResult,
                             @PathVariable("id") Long articleId,
                             Model model){
        if(bindingResult.hasErrors()){
            return "article/ArticleView"; // ??
        }

        Article article = articleService.getArticle(articleId);
        Comment parent = null;
        if(commentSaveForm.getParentId() != null){
            parent = commentService.findOne(commentSaveForm.getParentId());
        }

        commentService.saveComment(commentSaveForm, article, parent);

        // ajax 호출 시, 비동기 처리를 위해 여기서 list 쿼리 불러야함
        // 동시 등록 가능성을 고려하여 영속성컨텍스트도 비워야해서 true 로 넘김
        List<Comment> commentList = commentService.listCommentByArticle(articleId, true);
        model.addAttribute("commentList", commentList);

        return "article/articleView :: #commentList";
    }

    @GetMapping("/comment/list/{id}")
    public String listComment(@PathVariable("id") Long articleId,
                              Model model){
        log.info("list comment article id : {}", articleId);
        List<Comment> commentList = commentService.listCommentByArticle(articleId);

        model.addAttribute("commentList", commentList);

        return "article/articleView :: #commentList";
    }
}
