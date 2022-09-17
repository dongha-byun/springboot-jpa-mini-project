package project.notice.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Component
public class FileEncryptManager {

    public String encryptFileName(String plainFileName, String fileExt){
        log.info("plain file name : {}", plainFileName);

        String encrypt = UUID.randomUUID().toString();

        return encrypt+"."+fileExt;
    }
}
