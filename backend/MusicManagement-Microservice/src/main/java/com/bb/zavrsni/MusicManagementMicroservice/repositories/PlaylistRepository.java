package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Playlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, Integer> {

    @Query(value = "SELECT * FROM PLAYLIST WHERE CREATOR_ID=?1", nativeQuery = true)
    List<Playlist> findAllByCreatorId(int creator_id);

    @Query(value = "SELECT * FROM PLAYLIST WHERE LOWER(name) LIKE %?1%", nativeQuery = true)
    List<Playlist> searchPlaylists(@Param("str") String searchString, Pageable pageable);
}
