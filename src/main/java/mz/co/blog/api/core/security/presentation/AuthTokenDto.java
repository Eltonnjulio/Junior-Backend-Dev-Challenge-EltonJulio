package mz.co.blog.api.core.security.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import mz.co.blog.api.core.security.model.AuthToken;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AuthTokenDto {
  public String accessToken;

  @Setter(AccessLevel.PRIVATE)
  public String type = "Bearer";

  @JsonProperty("tokenTtl")
  private Long tokenTtl;

  public AuthTokenDto(AuthToken authToken) {
    accessToken = authToken.getToken();
    tokenTtl = ChronoUnit.MILLIS.between(LocalDateTime.now(), authToken.getValidUntil());
  }
}
