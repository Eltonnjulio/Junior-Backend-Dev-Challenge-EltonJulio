package mz.co.blog.api.upload;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileSystem implements FileSystem {
  private final Path basePath;

  private LocalFileSystem(Path basePath) {
    this.basePath = basePath;
  }

  @Override
  public ResponseEntity<InputStreamResource> download(String path, boolean asAttachment) throws IOException {
    File file = resolve(path).toFile();
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resolve(path)));

    if (asAttachment) {
      bodyBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
    }

    return  bodyBuilder.body(resource);
  }

  @Override
  public ResponseEntity<InputStreamResource> download(String path) throws IOException {
    return download(path, true);
  }

  @Override
  public boolean isFile(String path) {
    return resolve(path).toFile().isFile();
  }

  @Override
  public boolean isDirectory(String path) {
    return resolve(path).toFile().isDirectory();
  }

  public Path resolve(String path) {
    return basePath.resolve(path).toAbsolutePath();
  }

  @Override
  public Path getRootPath() {
    return basePath.resolve("");
  }

  @Override
  public Path move(String fromPath, String toPath) throws IOException {
    Path targetPath = resolve(toPath);
    targetPath.getParent().toFile().mkdirs();

    if(!Files.isDirectory(targetPath)){
      Files.deleteIfExists(targetPath);
    }

    Path newPath = Files.move(resolve(fromPath), resolve(toPath));
    Files.deleteIfExists(resolve(fromPath).getParent());

    return newPath;
  }

  public static LocalFileSystem of(String basePath) {
    return new LocalFileSystem(Paths.get(basePath).toAbsolutePath());
  }
}
