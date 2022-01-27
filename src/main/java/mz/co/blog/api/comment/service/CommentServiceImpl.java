package mz.co.blog.api.comment.service;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.comment.domain.Comment;
import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.domain.CommentMapper;
import mz.co.blog.api.comment.persistence.CommentRepository;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.post.domain.Post;
import mz.co.blog.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository repository;
    private final CommentMapper mapper;
    @Override
    public CommentJson create(Post post, CommentCommand command) {
        Comment comment = mapper.mapToModel(command);
        comment.setPost(post);
        comment.setCreatedBy(getLoggedUser());
        return mapper.mapToJson(repository.save(comment));
    }

    @Override
    public Comment findById(Long commentId) {
        return repository.findById(commentId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment Not found!!"));
    }

    @Override
    public Page<CommentJson> getPostComments(Long postId,Pageable pageable) {
        return mapper.mapToJson(repository.findByPostId(postId,pageable));
    }

    @Override
    public CommentJson update(Long commentId, CommentCommand command) {
        Comment comment =findById(commentId);
        mapper.updateModel(comment,command);
        return mapper.mapToJson(repository.save(comment));
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = findById(commentId);
        if(comment.getCreatedBy().getId() == getLoggedUser().getId()) {
            repository.delete(comment);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You're not allowed to delete this comment");
        }
    }
    private User getLoggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
