package project.notice.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "T_COMMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    // UUID
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;             // 내용
    private LocalDateTime writeDate;    // 작성일

    // 상위댓글정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 게시글 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // == 도메인 생성 메서드 == //
    public static Comment createComment(String content, Article article, Comment parent){
        Comment comment = new Comment();

        comment.content = content;
        comment.writeDate = LocalDateTime.now();

        comment.setArticle(article);
        if(parent != null){
            comment.parent = parent;
        }

        return comment;
    }

    // == 연관관계 편의 메서드 == //
    public void setArticle(Article article) {
        this.article = article;
        // 필요 시, 게시글과 양방향 연관관계 세팅
    }


}
