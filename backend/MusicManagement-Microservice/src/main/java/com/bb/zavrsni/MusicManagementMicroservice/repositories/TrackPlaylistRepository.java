package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.TrackPlaylist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrackPlaylistRepository extends CrudRepository<TrackPlaylist, Integer> {
    Optional<TrackPlaylist> findByTrackAndPlaylist(int track, int playlist);
}
