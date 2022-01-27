package mz.co.blog.api.upload.presentation;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.upload.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/files", name = "Files")
@Api(tags = "File Management")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/**")
    public ResponseEntity<?> streamFile(HttpServletRequest request) throws IOException {
        String path = "uploads/"+extractPath(request);
        return fileService.showImage(path);
    }

    @GetMapping(value = "/download/{uuid}/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@PathVariable String uuid, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        String path = "uploads/" + uuid + "/" + fileName;
        return fileService.download(path, false);
    }

    @PostMapping
    public ResponseEntity<FileJson> uploadFile(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }

    private String extractPath(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }
}
