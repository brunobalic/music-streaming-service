package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.TrackGenre;
import org.springframework.data.repository.CrudRepository;

public interface TrackGenreRepository extends CrudRepository<TrackGenre, Integer> {
}
