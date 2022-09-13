package project.notice.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createDate;       // 최초생성일자
    private Long createUserId;              // 최초생성자

    private LocalDateTime lastChangeDate;   // 최종변경일자
    private Long lastChangeUserId;          // 최종변경자

}
