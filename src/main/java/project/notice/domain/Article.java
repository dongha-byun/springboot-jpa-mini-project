package project.notice.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "T_ARTICLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "article_id")
    private Long id;
    private String title;               // 제목
    private LocalDateTime noticeDate;   // 게시일

    @Size(max = 1)
    private String delYn;            // 삭제여부

    @Lob
    private String content;             // 내용

    private Integer articleNo;          // 글번호

    private Integer readCnt;            // 조회수

    // 게시자 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User noticeUser;

    // 게시판 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<AttachFile> attachFileList = new ArrayList<>();

    public Article(String title, String content, Integer articleNo, User noticeUser, Board board) {
        this.title = title;
        this.noticeDate = LocalDateTime.now();
        this.delYn = "N";
        this.content = content;
        this.readCnt = 0;
        this.articleNo = articleNo;

        setNoticeUser(noticeUser);
        setBoard(board);
    }

    // 연관관계 편의 메서드
    // 작성자 정보 매핑
    public void setNoticeUser(User user){
        this.noticeUser = user;

        // 필요 시, 사용자도 양방향 연관관계 처리
    }

    // 게시판-게시글 매핑 등록
    public void setBoard(Board board){
        this.board = board;
        board.getArticleList().add(this);
    }

    // == 비지니스 로직 == //
    public void incrementReadCount(){
        this.readCnt++;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", noticeDate=" + noticeDate +
                ", delYn='" + delYn + '\'' +
                ", content='" + content + '\'' +
                ", articleNo=" + articleNo +
                ", readCnt=" + readCnt +
                '}';
    }
}
