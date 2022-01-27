package mz.co.blog.api.post.domain;

import lombok.Data;
import mz.co.blog.api.album.domain.Album;
import mz.co.blog.api.comment.domain.Comment;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.utils.JsonObjectConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String coverPicture;
    private String uuid;
    @Convert(converter = JsonObjectConverter.class)
    private List<String> images;
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    private List<Comment> comments;
    @ManyToOne
    private User createdBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
