package project.notice.form.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileSaveForm {

    private MultipartFile file;
    private String storeFileName;
    private String realFileName;

    private String fileExt;
    private String filePath;

    public FileSaveForm() {
    }

    // == form 생성 메서드 == //
    public static FileSaveForm createFileSaveForm(MultipartFile file, String storeFileName, String realFileName, String fileExt, String filePath){
        FileSaveForm fileSaveForm = new FileSaveForm();

        fileSaveForm.file = file;
        fileSaveForm.storeFileName = storeFileName;
        fileSaveForm.realFileName = realFileName;
        fileSaveForm.fileExt = fileExt;
        fileSaveForm.filePath = filePath;

        return fileSaveForm;
    }
}
