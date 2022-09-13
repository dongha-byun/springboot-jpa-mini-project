package project.notice.form.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class UserInsertForm {

    @NotBlank
    private String loginId;
    @NotBlank
    private String name;
    @NotBlank
    @Length(min = 8, max = 16)
    private String password;
    private String confirmPassword;
    @NotBlank
    private String telNo;
    private String nickName;

    public UserInsertForm(String loginId, String name, String password, String confirmPassword, String telNo, String nickName) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.telNo = telNo;
        this.nickName = nickName;
    }
}
