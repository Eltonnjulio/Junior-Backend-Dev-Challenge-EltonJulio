package mz.co.blog.api.comment.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.album.domain.AlbumCommand;
import mz.co.blog.api.album.presentation.AlbumJson;
import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Comment Management")
@RequestMapping(path = "/api/v1/comments", name = "comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PutMapping("/{id}")
    @ApiOperation("Update Comment Details")
    public ResponseEntity<CommentJson> updateComment(@PathVariable Long id, @RequestBody @Valid CommentCommand command) {
        return  ResponseEntity.ok(service.update(id,command));
    }
    @GetMapping("/{postId}")
    @ApiOperation("Get Post Comments")
    public ResponseEntity<Page<CommentJson>> getPostComments(@PathVariable Long postId, Pageable pageable) {
        return  ResponseEntity.ok(service.getPostComments(postId,pageable));
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete Comment")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
