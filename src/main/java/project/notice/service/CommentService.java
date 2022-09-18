package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.form.comment.CommentSaveForm;
import project.notice.repository.CommentRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(CommentSaveForm commentSaveForm, Article article, Comment parent){
        Comment comment = Comment.createComment(commentSaveForm.getContent(), article, parent);
        commentRepository.save(comment);
    }

    public Comment findOne(Long id){
        return commentRepository.findById(id)
                .orElseGet(() -> null);
    }
}
