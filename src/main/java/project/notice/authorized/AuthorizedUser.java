package project.notice.authorized;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizedUser {

    private Long id;
    private String name;
    private String nickName;

    public AuthorizedUser() {
    }

    public AuthorizedUser(Long id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }
}
