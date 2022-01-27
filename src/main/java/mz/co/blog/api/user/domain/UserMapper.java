package mz.co.blog.api.user.domain;

import mz.co.blog.api.user.presentation.UserJson;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class UserMapper {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Named("commandToModel")
    public abstract User mapToModel(CreateUserCommand command);

    @InheritConfiguration
    public abstract User mapToModel(UpdateUserCommand command);

    public abstract void updateModel(@MappingTarget User user, UpdateUserCommand command);

    @Mapping(target = "authorities", ignore = true)
    public abstract void cloneModel(@MappingTarget User user, User userSource);

    @InheritInverseConfiguration
    public abstract UserJson mapToJson(User user);
    public abstract List<UserJson> mapToJson(List<User> users);

    public Page<UserJson> mapToJson(Page<User> users) {
        return new PageImpl<>(mapToJson(users.getContent()), users.getPageable(), users.getTotalElements());
    }

}
