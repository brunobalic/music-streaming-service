package com.bb.zavrsni.MusicManagementMicroservice.repositories;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//public interface AlbumRepository extends CrudRepository<Album, Integer> {
public interface AlbumRepository extends PagingAndSortingRepository<Album, Integer> {

    @Query(value = "SELECT * FROM ALBUM WHERE ARTIST_ID=?1", nativeQuery = true)
    List<Album> findAllByArtistId(int artist_id);

    @Query(value = "SELECT * FROM ALBUM WHERE LOWER(name) LIKE %?1%", nativeQuery = true)
    List<Album> searchAlbums(@Param("str") String searchString, Pageable pageable);
}
