package project.notice.form.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardEditForm {
    private Long id;
    @NotBlank
    private String name;
    private String description;

    public BoardEditForm(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
