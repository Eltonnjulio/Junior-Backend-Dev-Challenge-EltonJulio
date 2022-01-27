package mz.co.blog.api.post.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.comment.domain.CommentCommand;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.post.domain.PostCommand;
import mz.co.blog.api.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Post Management")
@RequestMapping(path = "/api/v1/posts", name = "countries")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ApiOperation("Create a new post")
    public ResponseEntity<PostJson> create(@RequestBody @Valid PostCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(command));
    }
    @PostMapping("/{id}/comment")
    @ApiOperation("Comment post")
    public ResponseEntity<CommentJson> commentPost(@PathVariable Long id, @RequestBody @Valid CommentCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.comment(id,command));
    }

    @GetMapping("/{id}")
    @ApiOperation("Fetch Post By Id")
    public ResponseEntity<PostJson> getById(@PathVariable Long id) {
            return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping
    @ApiOperation("Fetch All posts")
    public ResponseEntity<Page<PostJson>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Post Details")
    public ResponseEntity<PostJson> update(@PathVariable Long id, @RequestBody @Valid PostCommand command) {
        return  ResponseEntity.ok(postService.update(command,id));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("Delete Post")
    public ResponseEntity<?> delete(@PathVariable Long id) {
            postService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
