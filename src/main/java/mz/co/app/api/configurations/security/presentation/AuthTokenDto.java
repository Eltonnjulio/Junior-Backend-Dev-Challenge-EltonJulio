package mz.co.app.api.configurations.security.presentation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

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
