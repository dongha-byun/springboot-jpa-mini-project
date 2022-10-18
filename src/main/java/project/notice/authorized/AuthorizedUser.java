package project.notice.authorized;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.notice.domain.Grade;

@Getter
@Setter
@ToString
public class AuthorizedUser {

    private Long id;
    private String name;
    private String nickName;

    private Grade grade;

    public AuthorizedUser() {
    }

    public AuthorizedUser(Long id, String name, String nickName, Grade grade) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.grade = grade;
    }
}
