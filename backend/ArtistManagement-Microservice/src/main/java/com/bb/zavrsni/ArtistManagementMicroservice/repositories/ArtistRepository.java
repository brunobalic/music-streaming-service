package com.bb.zavrsni.ArtistManagementMicroservice.repositories;

import com.bb.zavrsni.ArtistManagementMicroservice.models.entities.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends PagingAndSortingRepository<Artist, Integer> {
    Optional<Artist> findByAuth0id(String auth0id);
    @Query(value = "SELECT * FROM ARTIST WHERE LOWER(first_name) LIKE %?1% OR LOWER(last_name) LIKE %?1%", nativeQuery = true)
    List<Artist> searchArtists(@Param("str") String searchString, Pageable pageable);
}
