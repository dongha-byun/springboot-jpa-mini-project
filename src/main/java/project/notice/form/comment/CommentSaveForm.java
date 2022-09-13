package project.notice.form.comment;

import lombok.Data;

@Data
public class CommentSaveForm {

    private String content;
    private Long articleId;
    private Long parentId;
}
