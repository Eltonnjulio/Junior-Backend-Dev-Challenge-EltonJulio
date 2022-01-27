package mz.co.blog.api.album.domain;

import lombok.Data;

import java.util.List;

@Data
public class AlbumCommand {
    private String title;
    private List<String> images;
}
