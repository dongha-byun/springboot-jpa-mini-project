package project.notice.form.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.notice.domain.Grade;

@Data
public class UserDto {

    private String loginId;
    private String name;
    private String telNo;
    private Grade grade;
    private String nickName;

    public UserDto(String loginId, String name, String telNo, Grade grade, String nickName) {
        this.loginId = loginId;
        this.name = name;
        this.telNo = telNo;
        this.grade = grade;
        this.nickName = nickName;
    }
}
