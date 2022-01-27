package mz.co.blog.api.comment.domain;

import mz.co.blog.api.album.domain.Album;
import mz.co.blog.api.comment.presentation.CommentJson;
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
public abstract class CommentMapper {
    public static CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Named("commandToModel")
    public abstract Comment mapToModel(CommentCommand command);

    public abstract void updateModel(@MappingTarget Comment comment, CommentCommand command);


    public abstract void cloneModel(@MappingTarget Comment comment, Album albumSource);

    @InheritInverseConfiguration
    public abstract CommentJson mapToJson(Comment comment);
    public abstract List<CommentJson> mapToJson(List<Comment> comments);

    public Page<CommentJson> mapToJson(Page<Comment> comments) {
        return new PageImpl<>(mapToJson(comments.getContent()), comments.getPageable(), comments.getTotalElements());
    }
    @AfterMapping
    public void setUser(@MappingTarget CommentJson commentJson,Comment comment){
        commentJson.setCreatedBy(UserMapper.INSTANCE.mapToJson(comment.getCreatedBy()));
    }
}
