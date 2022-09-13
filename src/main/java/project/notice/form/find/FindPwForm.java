package project.notice.form.find;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPwForm extends FindIdForm{

    @NotBlank
    private String loginId;
}
