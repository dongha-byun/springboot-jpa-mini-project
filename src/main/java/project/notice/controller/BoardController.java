package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.notice.domain.Article;
import project.notice.service.ArticleService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String list(Model model){

        List<Article> articleList = articleService.articleListAll();

        model.addAttribute("articleList", articleList);

        return "board/listMain";
    }
}
