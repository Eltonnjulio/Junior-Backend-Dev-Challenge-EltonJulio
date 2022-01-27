package mz.co.blog.api.user.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateUserCommand {

    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String name;
}
