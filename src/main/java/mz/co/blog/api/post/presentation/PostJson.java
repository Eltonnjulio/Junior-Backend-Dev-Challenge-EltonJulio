package mz.co.blog.api.post.presentation;

import lombok.Data;
import mz.co.blog.api.comment.domain.Comment;
import mz.co.blog.api.comment.presentation.CommentJson;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.user.presentation.UserJson;
import mz.co.blog.api.utils.JsonObjectConverter;

import javax.persistence.Convert;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostJson {
    private Long id;
    private String title;
    private String description;
    private String coverPicture;
    private String uuid;
    private List<String> images;
    private UserJson createdBy;
}
