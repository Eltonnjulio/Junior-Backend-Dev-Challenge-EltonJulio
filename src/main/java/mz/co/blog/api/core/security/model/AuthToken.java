package mz.co.blog.api.core.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mz.co.blog.api.user.domain.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class AuthToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private LocalDateTime validUntil;
  private LocalDateTime refreshUntil;
  private boolean isBlacklisted;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public AuthToken(String token, LocalDateTime validUntil, LocalDateTime refreshUntil, User user) {
    this.token = token;
    this.validUntil = validUntil;
    this.refreshUntil = refreshUntil;
    this.user = user;
    this.isBlacklisted = false;
  }
}
