package mz.co.blog.api.user.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class CreateUserCommand {
    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String name;

    @Email
    private String email;

    @NotNull
    private UserRole role;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String passwordConfirmation;
}
