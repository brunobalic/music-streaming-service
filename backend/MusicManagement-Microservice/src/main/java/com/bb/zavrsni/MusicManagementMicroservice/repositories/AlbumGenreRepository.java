package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.AlbumGenre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlbumGenreRepository extends CrudRepository<AlbumGenre, Integer> {
    Optional<AlbumGenre> findByAlbumAndGenre(int album, int genre);
}
