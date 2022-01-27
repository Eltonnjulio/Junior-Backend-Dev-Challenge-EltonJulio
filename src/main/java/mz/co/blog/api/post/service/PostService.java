package mz.co.blog.api.post.service;

import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.post.domain.Post;
import mz.co.blog.api.post.domain.PostCommand;
import mz.co.blog.api.post.presentation.PostJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostJson> findAll(Pageable pageable);
    PostJson create(PostCommand command);
    PostJson update(PostCommand command, Long postId);
    Post findById(Long postId);
    void delete(Long postId);
    PostJson getById(Long postId);
    CommentJson comment(Long postId, CommentCommand command);
}
