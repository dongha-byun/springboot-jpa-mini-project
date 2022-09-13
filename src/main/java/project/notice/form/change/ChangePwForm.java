package project.notice.form.change;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ChangePwForm {

    @NotBlank
    private String orgPassword;
    private String chkOrgPassword;
    @NotBlank
    private String chgPassword;
    @NotBlank
    private String chgPasswordConfirm;

    private Long targetUserId;

    public ChangePwForm(String orgPassword, String chgPassword, String chgPasswordConfirm) {
        this.orgPassword = orgPassword;
        this.chgPassword = chgPassword;
        this.chgPasswordConfirm = chgPasswordConfirm;
    }

    // == 이전 비밀번호 체크 값 생성 == //
    public static ChangePwForm createFormWithChkPw(String chkOrgPassword, Long targetUserId){
        ChangePwForm changePwForm = new ChangePwForm();
        changePwForm.setChkOrgPassword(chkOrgPassword);
        changePwForm.setTargetUserId(targetUserId);

        return changePwForm;
    }

    // == validation 체크 로직 추가 == //
    // 기존 비밀번호 일치여부 검증
    public boolean isEqualOrgPassword(){
        return this.orgPassword.equals(this.chkOrgPassword);
    }

    // 변경 비밀번호 랑 변경 비밀번호 확인이랑 같은지 검증
    public boolean isEqualChgPassword(){
        return this.chgPassword.equals(this.chgPasswordConfirm);
    }
}
