package mz.co.blog.api.comment.service;

import mz.co.blog.api.comment.domain.Comment;
import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentJson create(Post post,CommentCommand command);
    CommentJson update(Long commentId,CommentCommand command);
    void delete(Long commentId);
    Comment findById(Long commentId);
    Page<CommentJson> getPostComments(Long postId,Pageable pageable);
}
