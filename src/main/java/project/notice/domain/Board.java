package project.notice.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.notice.form.board.BoardEditForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "T_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String boardName;       // 게시판명
    private String description;     // 게시판 설명

    @OneToMany(mappedBy = "board")
    private List<Article> articleList = new ArrayList<>();

    public Board(String boardName, String description) {
        this.boardName = boardName;
        this.description = description;
    }

    // == 비지니스 로직 == //
    public void updateBoard(BoardEditForm editForm){
        this.boardName = editForm.getName();
        this.description = editForm.getDescription();
    }
}
