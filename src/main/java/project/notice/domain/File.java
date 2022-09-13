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

    private String fileName;    // 파일명
    private String ext;         // 확장자
    private String filePath;    // 파일경로

    // 게시글 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // == 도메인 생성 메서드 == //
    public static File createFile(String fileName, String ext, String filePath, Article article){
        File file = new File();
        file.setFileName(fileName);
        file.setExt(ext);
        file.setFilePath(filePath);
        file.setArticle(article);

        return file;
    }

    // == 도메인 생성을 위한 private 생성자 == //
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void setExt(String ext) {
        this.ext = ext;
    }

    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void setArticle(Article article) {
        this.article = article;
    }
}
