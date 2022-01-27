package mz.co.blog.api.album.service;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.album.domain.Album;
import mz.co.blog.api.album.domain.AlbumCommand;
import mz.co.blog.api.album.domain.AlbumMapper;
import mz.co.blog.api.album.persistence.AlbumRepository;
import mz.co.blog.api.album.presentation.AlbumJson;
import mz.co.blog.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumMapper mapper;
    private final AlbumRepository repository;

    @Override
    public AlbumJson create(AlbumCommand command) {
        Album album = mapper.mapToModel(command);
        album.setCreatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return mapper.mapToJson(repository.save(album));
    }

    @Override
    public AlbumJson update(Long provinceId, AlbumCommand command) {
        Album album = findById(provinceId);
        mapper.updateModel(album, command);
        return mapper.mapToJson(repository.save(album));
    }

    @Override
    public AlbumJson getProvinceById(Long provinceId) {
        return mapper.mapToJson(findById(provinceId));
    }

    @Override
    public Page<AlbumJson> fetchProvinces(Pageable pageable) {
        return mapper.mapToJson(repository.findAll(pageable));
    }

    @Override
    public Album findById(Long provinceId) {
        return repository.findById(provinceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
    }

    @Override
    public void delete(Long provinceId) {
        Album album = findById(provinceId);
        if (album.getCreatedBy().getId() == getLoggedUser().getId()) {
            repository.delete(album);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not allowed to delete this album");
        }
    }

    private User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
