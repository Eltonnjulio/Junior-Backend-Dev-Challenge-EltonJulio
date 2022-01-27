package mz.co.blog.api.post.domain;

import lombok.Data;
import java.util.List;

@Data
public class PostCommand {
    private String title;
    private String description;
    private String coverPicture;
    private List<String> images;
}
