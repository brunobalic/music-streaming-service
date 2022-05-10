package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.ArtistTrack;
import org.springframework.data.repository.CrudRepository;

public interface ArtistTrackRepository extends CrudRepository<ArtistTrack, Integer> {
}
