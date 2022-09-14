package project.notice.form.article;

import lombok.Data;

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
}
