package project.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.util.UriUtils;
import project.notice.authorized.AuthorizedUser;
import project.notice.domain.AttachFile;
import project.notice.service.FileService;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttachFile(@PathVariable("fileId")Long id) throws MalformedURLException {

        AttachFile findFile = fileService.findOne(id);
        String realFileName = findFile.getRealFileName();
        String storeFileName = findFile.getStoreFileName();

        UrlResource urlResource = new UrlResource("file:" + fileService.getFileFullPath(storeFileName));

        // 한글 파일명 깨짐 방지
        String encodingName = UriUtils.encode(realFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; fileName=\"" + encodingName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
