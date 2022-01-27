package mz.co.blog.api.album.service;

import mz.co.blog.api.album.domain.AlbumCommand;
import mz.co.blog.api.album.presentation.AlbumJson;
import mz.co.blog.api.album.domain.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumService {
   AlbumJson create(AlbumCommand command);
   AlbumJson update(Long provinceId, AlbumCommand command);
   AlbumJson getProvinceById(Long provinceId);
   Page<AlbumJson> fetchProvinces(Pageable pageable);
   Album findById(Long provinceId);
   void delete(Long provinceId);
}
