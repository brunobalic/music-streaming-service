package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.ArtistAlbum;
import org.springframework.data.repository.CrudRepository;

public interface ArtistAlbumRepository extends CrudRepository<ArtistAlbum, Integer> {
}
