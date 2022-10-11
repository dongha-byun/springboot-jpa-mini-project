package project.notice.form.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserEditForm {

    @NotBlank
    private String nickName;

    @NotBlank
    private String telNo;
}
