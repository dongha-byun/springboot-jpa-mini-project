package project.notice.manager;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataSecurityManager {

    public String maskingLoginId(String plain){
        String subStr = plain.substring(plain.length()-3);
        String masking = StringUtils.replace(plain, subStr, "***");

        return masking;
    }
}
