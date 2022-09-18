package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.form.comment.CommentSaveForm;
import project.notice.service.ArticleService;
import project.notice.service.CommentService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/comment/add/{id}")
    public String addComment(@Validated @ModelAttribute("commentSaveForm") CommentSaveForm commentSaveForm,
                             BindingResult bindingResult,
                             @PathVariable("id") Long articleId){
        if(bindingResult.hasErrors()){
            return "article/ArticleView"; // ??
        }

        Article article = articleService.getArticle(articleId);
        Comment parent = null;
        if(commentSaveForm.getParentId() != null){
            parent = commentService.findOne(commentSaveForm.getParentId());
        }

        commentService.saveComment(commentSaveForm, article, parent);

        return "redirect:/article/{id}";
    }
}
