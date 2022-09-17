package project.notice.repository;

import project.notice.domain.AttachFile;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    AttachFile save(AttachFile file);
    Optional<AttachFile> findById(Long id);
}
