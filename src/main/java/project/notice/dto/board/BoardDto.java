package project.notice.dto.board;

import lombok.Data;

@Data
public class BoardDto {
    private Long id;
    private String name;
    private String description;

    public BoardDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
