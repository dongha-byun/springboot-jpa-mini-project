package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.notice.authorized.AuthorizedUser;
import project.notice.domain.*;
import project.notice.dto.pop.CommonPopInfo;
import project.notice.form.article.ArticleWriteForm;
import project.notice.form.comment.CommentSaveForm;
import project.notice.service.*;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private static final String ARTICLE_WRITE_PAGE = "article/articleWrite";

    private final ArticleService articleService;
    private final BoardService boardService;
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/article/write")
    public String articleWritePage(@ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                                   Model model){

        List<Board> boardList = boardService.findBoardAll();
        model.addAttribute("boardList", boardList);

        return ARTICLE_WRITE_PAGE;
    }

    @PostMapping("/article/write")
    public String articleWrite(@Validated  @ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm,
                               BindingResult bindingResult,
                               @RequestParam MultipartFile file,
                               @RequestAttribute AuthorizedUser authorizedUser) throws IOException{
        if(bindingResult.hasErrors()){
            return ARTICLE_WRITE_PAGE;
        }

        if(authorizedUser == null){
            bindingResult.reject("sessionExpire", "세션 정보가 유효하지 않습니다. 다시 시도해주세요.");
            return ARTICLE_WRITE_PAGE;
        }

        Board board = boardService.findOne(articleWriteForm.getBoardId());
        if(board == null){
            bindingResult.reject("noAuthBoard", "작성 권한이 없는 게시판에 작성이 불가합니다.");
            return ARTICLE_WRITE_PAGE;
        }

        User user = userService.findOne(authorizedUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자 인증 실패")
        );
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
    public String editArticlePage(@PathVariable("id") Long id, Model model){

        log.info("=== go article edit page == id : {}", id);

        Article article = articleService.getArticle(id);
        ArticleWriteForm articleWriteForm = ArticleWriteForm.convertEntityToForm(article);
        List<Board> boardList = boardService.findBoardAll();

        model.addAttribute("articleWriteForm", articleWriteForm);
        model.addAttribute("article", article);
        model.addAttribute("boardList", boardList);


        return "article/articleEdit";
    }

    @PostMapping("/article/{id}/edit")
    public String editArticle(@PathVariable("id") Long id,
                              @Validated @ModelAttribute("articleWriteForm") ArticleWriteForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "article/articleEdit";
        }

        // 게시글 수정 로직 추가
        // service 에 위임해서 변경감지 활용
        form.setBoard(boardService.findOne(form.getBoardId()));
        articleService.editArticle(id, form);

        redirectAttributes.addAttribute("id", id);

        return "redirect:/article/{id}";
    }

    @PostMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id,
                                Model model){
        log.info("=== article delete ===");

        articleService.deleteArticle(id);

        CommonPopInfo commonPopInfo = new CommonPopInfo("성공적으로 삭제되었습니다.", "/board/list");
        model.addAttribute(CommonPopInfo.MODEL_NAME, commonPopInfo);

        return CommonPopInfo.POP_PAGE;
    }



}
