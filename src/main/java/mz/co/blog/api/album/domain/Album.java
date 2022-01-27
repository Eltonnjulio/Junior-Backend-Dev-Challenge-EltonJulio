package mz.co.blog.api.album.domain;

import lombok.Data;
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
@Table(name = "albums")
@Data
@SQLDelete(sql = "UPDATE albums SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Convert(converter = JsonObjectConverter.class)
    private List<String> images;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    private User createdBy;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
