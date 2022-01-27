package mz.co.blog.api.album.domain;

import mz.co.blog.api.album.presentation.AlbumJson;
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
public abstract class AlbumMapper {
    public static AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    @Named("commandToModel")
    public abstract Album mapToModel(AlbumCommand command);

    public abstract void updateModel(@MappingTarget Album album, AlbumCommand command);


    public abstract void cloneModel(@MappingTarget Album album, Album albumSource);

    @InheritInverseConfiguration
    public abstract AlbumJson mapToJson(Album album);
    public abstract List<AlbumJson> mapToJson(List<Album> albums);

    public Page<AlbumJson> mapToJson(Page<Album> provinces) {
        return new PageImpl<>(mapToJson(provinces.getContent()), provinces.getPageable(), provinces.getTotalElements());
    }

    @AfterMapping
    public void setUser(@MappingTarget AlbumJson albumJson,Album album){
        albumJson.setCreatedBy(UserMapper.INSTANCE.mapToJson(album.getCreatedBy()));
    }
}
