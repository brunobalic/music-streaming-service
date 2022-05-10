package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.TrackAlbum;
import org.springframework.data.repository.CrudRepository;

public interface TrackAlbumRepository extends CrudRepository<TrackAlbum, Integer> {
}
