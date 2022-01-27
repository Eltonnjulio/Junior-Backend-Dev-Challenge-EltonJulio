package mz.co.blog.api.core.security.persistence;

import mz.co.blog.api.core.security.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
  Optional<AuthToken> findByToken(String token);

  @Modifying
  @Query(value = "DELETE FROM auth_tokens WHERE refresh_until <= CURRENT_TIMESTAMP() OR is_blacklisted = TRUE", nativeQuery = true)
  void deleteExpiredTokens();
}
