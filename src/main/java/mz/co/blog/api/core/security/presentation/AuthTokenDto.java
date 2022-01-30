package mz.co.blog.api.core.security.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AuthTokenDto {
  public String accessToken;

  @Setter(AccessLevel.PRIVATE)
  public String type = "Bearer";

  public AuthTokenDto(String accessToken, String type) {
    this.accessToken = accessToken;
    this.type = type;
  }
}
