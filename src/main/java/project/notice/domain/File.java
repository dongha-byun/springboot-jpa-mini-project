package project.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity
@Table(name = "T_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String ext;         // 확장자
    private String filePath;    // 파일경로
    private String realFileName; // 실제 파일명
    private String storeFileName; // 저장 파일명

    // 게시글 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // == 도메인 생성 메서드 == //
    public static File createFile(String realFileName, String storeFileName, String ext, String filePath, Article article){
        File file = new File();

        file.realFileName = realFileName;
        file.storeFileName = storeFileName;
        file.ext = ext;
        file.filePath = filePath;
        file.mappedByArticle(article);

        return file;
    }

    // == 연관관계 편의 메서드 == //
    public void mappedByArticle(Article article){
        this.article = article;
    }
}
