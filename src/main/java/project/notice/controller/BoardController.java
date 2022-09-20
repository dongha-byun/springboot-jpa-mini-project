package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.notice.domain.Article;
import project.notice.domain.Board;
import project.notice.service.ArticleService;
import project.notice.service.BoardService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    //public String list(@RequestParam("id") Long boardId, Model model){
    public String list(Model model){
        log.info("method : list");

        List<Board> boardList = boardService.findBoardAll();
        List<Article> articleList = boardList.get(0).getArticleList();

        model.addAttribute("articleList", articleList);
        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    @GetMapping("/list/{id}")
    public String listByBoard(@PathVariable("id") Long id, Model model){
        log.info("method : listByBoard");

        Board board = boardService.findOne(id);
        List<Article> articleList = board.getArticleList();
        model.addAttribute("articleList", articleList);

        return "board/list :: #articleListTable";
    }
}
