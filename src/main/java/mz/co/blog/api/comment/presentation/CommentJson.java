package mz.co.blog.api.comment.presentation;

import lombok.Data;
import mz.co.blog.api.user.presentation.UserJson;

import java.time.LocalDateTime;
@Data
public class CommentJson {
    private Long id;
    private String comment;
    private UserJson createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
