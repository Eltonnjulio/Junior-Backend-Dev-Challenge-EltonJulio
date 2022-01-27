package mz.co.blog.api.upload.presentation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileJson {
    private String name;
    private String path;

    public static FileJson toJson(String name, String path) {
        FileJson json = new FileJson();
        json.setName(name);
        json.setPath(path);
        return json;
    }
}
