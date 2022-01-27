package mz.co.blog.api.post.service;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.comment.service.CommentService;
import mz.co.blog.api.post.domain.Post;
import mz.co.blog.api.post.domain.PostCommand;
import mz.co.blog.api.post.domain.PostMapper;
import mz.co.blog.api.post.persistence.PostRepository;
import mz.co.blog.api.post.presentation.PostJson;
import mz.co.blog.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper MAPPER = PostMapper.INSTANCE;
    private final CommentService commentService;

    @Override
    public PostJson create(PostCommand command) {
        Post post = MAPPER.mapToModel(command);
        post.setUuid(UUID.randomUUID().toString());
        post.setCreatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return MAPPER.mapToJson(repository.save(post));
    }

    @Override
    public PostJson update(PostCommand command, Long id) {
        Post post = findById(id);
        MAPPER.updateModel(post, command);
        return MAPPER.mapToJson(repository.save(post));
    }

    @Override
    public void delete(Long id) {
        Post post = findById(id);
        if (post.getCreatedBy().getId() == getLoggedUser().getId()) {
            repository.delete(post);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not allowed to delete this post");
        }
    }

    private User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public PostJson getById(Long id) {
        return MAPPER.mapToJson(findById(id));
    }

    @Override
    public CommentJson comment(Long postId, CommentCommand command) {
        Post post = findById(postId);
        return commentService.create(post, command);
    }

    @Override
    public Page<PostJson> findAll(Pageable pageable) {
        return MAPPER.mapToJson(repository.findAll(pageable));
    }

    @Override
    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }
}
