package project.notice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Comment;
import project.notice.form.comment.CommentSaveForm;

@Transactional(readOnly = true)
@Service
public class CommentService {

    public void saveComment(CommentSaveForm commentSaveForm){

    }
}
