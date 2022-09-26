package project.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.notice.domain.Article;
import project.notice.domain.Comment;
import project.notice.domain.User;
import project.notice.form.comment.CommentSaveForm;
import project.notice.repository.CommentRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(CommentSaveForm commentSaveForm, Article article, User user, Comment parent){
        Comment comment = Comment.createComment(commentSaveForm.getContent(), article, user, parent);
        commentRepository.save(comment);
    }

    public Comment findOne(Long id){
        return commentRepository.findById(id)
                .orElseGet(() -> null);
    }

    public List<Comment> listCommentByArticle(Long articleId){
        return listCommentByArticle(articleId, false);
    }

    public List<Comment> listCommentByArticle(Long articleId, boolean isClearPersistenceContext){
        if(isClearPersistenceContext){
            commentRepository.flushAndClear();
        }
        return commentRepository.findAllByArticleId(articleId);

    }
}
