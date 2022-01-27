package mz.co.blog.api.core.security.model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthLoginRequest {
  @ApiModelProperty(required = true, position = -1, example = "admin")
  @NotNull
  private String username;

  @ApiModelProperty(required = true, example = "secret")
  @NotNull
  private String password;
}
