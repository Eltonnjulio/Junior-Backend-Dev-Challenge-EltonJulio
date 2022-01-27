package mz.co.blog.api.post.domain;

import mz.co.blog.api.comment.domain.CommentMapper;
import mz.co.blog.api.post.presentation.PostJson;
import mz.co.blog.api.user.domain.UserMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class PostMapper {
    public static PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Named("commandToModel")
    public abstract Post mapToModel(PostCommand command);


    public abstract void updateModel(@MappingTarget Post post, PostCommand command);


    public abstract void cloneModel(@MappingTarget Post post, Post postSource);

    @InheritInverseConfiguration
    public abstract PostJson mapToJson(Post post);
    public abstract List<PostJson> mapToJson(List<Post> countries);

    public Page<PostJson> mapToJson(Page<Post> countries) {
        return new PageImpl<>(mapToJson(countries.getContent()), countries.getPageable(), countries.getTotalElements());
    }

    @AfterMapping
    public void setUserAndComments(@MappingTarget PostJson postJson, Post post){
        postJson.setCreatedBy(UserMapper.INSTANCE.mapToJson(post.getCreatedBy()));
    }
}
