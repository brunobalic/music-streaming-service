package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Track;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends PagingAndSortingRepository<Track, Integer> {
    @Query(value = "SELECT * FROM TRACK WHERE LOWER(title) LIKE %?1%", nativeQuery = true)
    List<Track> searchTracks(@Param("str") String searchString, Pageable pageable);

    @Query(value = "SELECT t.id FROM TRACK t WHERE t.artist_id = ?1")
    List<Integer> findByArtist_id(int artistIid);
}
