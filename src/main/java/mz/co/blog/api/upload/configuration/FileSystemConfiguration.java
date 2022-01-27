package mz.co.blog.api.upload.configuration;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.upload.FileSystem;
import mz.co.blog.api.upload.LocalFileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class FileSystemConfiguration {
  private final Environment env;

  @Bean
  FileSystem fileSystem() {
    String storagePath = env.getProperty("app.storage.path", "storage");
    return LocalFileSystem.of(storagePath);
  }
}
