package project.notice.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "T_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;             // 로그인아이디
    private String password;            // 비밀번호
    private String name;                // 사용자이름
    private String telNo;               // 연락처

    @Enumerated(EnumType.STRING)
    private Grade grade;                // 사용자 등급

    private String nickName;            // 사용자 별명(닉네임)
    private Integer loginFailCnt = 0;   // 로그인 실패 횟수

    public User(String loginId, String password, String name, String telNo, String nickName) {
        this(loginId, password, name, telNo, Grade.GUEST, nickName, 0);
    }

    public User(String loginId, String password, String name, String telNo, Grade grade, String nickName, Integer loginFailCnt) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.telNo = telNo;
        this.grade = grade;
        this.nickName = nickName;
        this.loginFailCnt = loginFailCnt;
    }

    // == 도메인 생성 메서드 == //
    public static User createUser(String loginId, String password, String name, String telNo, Grade grade, String nickName, Integer loginFailCnt){
        User user = new User();

        user.loginId = loginId;
        user.password = password;
        user.name = name;
        user.telNo = telNo;
        user.grade = grade;
        user.nickName = nickName;
        user.loginFailCnt = loginFailCnt;

        return user;
    }

    // == 비지니스 로직 == //
    public void changePassword(String password){
        this.password = password;
        initLoginFailCnt();
    }

    public void initLoginFailCnt(){
        this.loginFailCnt = 0;
    }
}
