package mz.co.blog.api.upload.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import mz.co.blog.api.upload.FileSystem;
import mz.co.blog.api.upload.presentation.FileJson;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Environment env;
    private final FileSystem fileSystem;

    @SneakyThrows
    @Override
    public FileJson upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IOException("Ficheiro inválido");
        }
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
        Path uploadPath = Paths.get(Optional.ofNullable(env.getProperty("app.upload_path")).orElse("storage/uploads"));

        // Make sure upload folder exists
        uploadPath.toFile().mkdirs();

        byte[] bytes = file.getBytes();

        // Generate a UUID and factory a folder with it to prevent file collision
        String uuid = UUID.randomUUID().toString();
        Path filePath = Paths.get(uploadPath.toString() + "/" + uuid);
        filePath.toFile().mkdirs();

        Path pathWithName = Paths.get(String.join("/", filePath.toString(), fileName));
        Files.write(pathWithName, bytes);

        String path = String.join("/", uuid, fileName);
        return FileJson.toJson(fileName, path);
    }

    @Override
    public java.io.File getFile(String uuid, String fileName) throws FileNotFoundException {
        String uploadPath = Optional.ofNullable(env.getProperty("app.upload_path")).orElse("");
        String filePath = String.join("/",uploadPath, uuid, fileName);
        return new java.io.File(filePath);
    }

    public ResponseEntity<InputStreamResource> download(String path, boolean stream) throws IOException {
        return fileSystem.download(path, stream);
    }

    @Override
    public ResponseEntity<byte[]> showImage(String path) throws IOException {
        InputStreamResource resource = fileSystem.download(path,false).getBody();
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @Override
    public Path move(String fromPath, String toPath) throws IOException {
        return fileSystem.move(fromPath, toPath);
    }

    @Override
    public String getExtension(java.io.File file) {
        String[] ext = file.getName().split("\\.");
        return ext.length > 0 ? ext[ext.length - 1] : "";
    }
}
