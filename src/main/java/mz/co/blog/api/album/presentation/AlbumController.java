package mz.co.blog.api.album.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.album.domain.AlbumCommand;
import mz.co.blog.api.album.service.AlbumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Album Management")
@RequestMapping(path = "/api/v1/provinces", name = "provinces")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @PostMapping
    @ApiOperation("Create a new album")
    public ResponseEntity<AlbumJson> createAlbum(@RequestBody @Valid AlbumCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.create(command));
    }

    @GetMapping("/{id}")
    @ApiOperation("Fetch Album By Id")
    public ResponseEntity<AlbumJson> getAlbumById(@PathVariable Long id) {
            return ResponseEntity.ok(albumService.getProvinceById(id));
    }

    @GetMapping
    @ApiOperation("Fetch All provinces")
    public ResponseEntity<Page<AlbumJson>> getAlbums(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(albumService.fetchProvinces(pageable));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Album Details")
    public ResponseEntity<AlbumJson> updateAlbum(@PathVariable Long id, @RequestBody @Valid AlbumCommand command) {
        return  ResponseEntity.ok(albumService.update(id,command));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("Delete Album")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
            albumService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
