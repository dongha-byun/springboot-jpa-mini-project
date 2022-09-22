package project.notice.form.article;

import lombok.Data;
import project.notice.domain.Article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleWriteForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long boardId;

    // == 변환 메서드 == //
    public static ArticleWriteForm convertEntityToForm(Article article){
        ArticleWriteForm articleWriteForm = new ArticleWriteForm();

        articleWriteForm.title = article.getTitle();
        articleWriteForm.content = article.getContent();
        articleWriteForm.boardId = article.getBoard().getId();

        return articleWriteForm;
    }
}
