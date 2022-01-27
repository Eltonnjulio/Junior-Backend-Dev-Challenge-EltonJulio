package mz.co.blog.api.upload;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSystem {
  ResponseEntity<InputStreamResource> download(String path, boolean asAttachment) throws IOException;
  ResponseEntity<InputStreamResource> download(String path) throws IOException;
  boolean isFile(String path);
  boolean isDirectory(String path);
  Path resolve(String path);
  Path getRootPath();
  Path move(String fromPath, String toPath) throws IOException;
}
