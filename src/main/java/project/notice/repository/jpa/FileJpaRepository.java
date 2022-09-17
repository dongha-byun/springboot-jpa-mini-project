package project.notice.repository.jpa;

import lombok.RequiredArgsConstructor;
import project.notice.domain.AttachFile;
import project.notice.repository.FileRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FileJpaRepository implements FileRepository {

    private final EntityManager em;

    @Override
    public AttachFile save(AttachFile file) {
        em.persist(file);
        return file;
    }

    @Override
    public Optional<AttachFile> findById(Long id) {
        return Optional.ofNullable(em.find(AttachFile.class, id));
    }

}
