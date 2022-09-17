package project.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.notice.domain.Article;
import project.notice.domain.AttachFile;
import project.notice.form.file.FileSaveForm;
import project.notice.manager.FileEncryptManager;
import project.notice.repository.FileRepository;

import java.io.IOException;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileEncryptManager fileEncryptManager;

    @Value("${file.dir}")
    public String fileDir;

    @Transactional
    public void saveFile(AttachFile file) {
        fileRepository.save(file);
    }

    public FileSaveForm transferToFile(MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return null;
        }

        // 물리파일 저장
        String fileName = file.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        String encryptFileName = fileEncryptManager.encryptFileName(fileName, fileExt);

        String fullPath = fileDir + encryptFileName; // 파일 디렉토리 경로 + 파일명
        log.info("fullPath={}", fullPath);
        file.transferTo(new java.io.File(fullPath)); // 파일 저장

        return FileSaveForm.createFileSaveForm(file, encryptFileName, fileName, fileExt, fullPath);
    }

    @Transactional
    public void saveFile(MultipartFile file, Article article) throws IOException{
        FileSaveForm fileSaveForm = transferToFile(file);
        if(fileSaveForm != null){
            AttachFile attachFile = AttachFile.createFile(fileSaveForm.getRealFileName(), fileSaveForm.getStoreFileName(),
                                                            fileSaveForm.getFileExt(), fileSaveForm.getFilePath(), article);
            saveFile(attachFile);
        }
    }
}
