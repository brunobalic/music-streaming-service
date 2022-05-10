package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserRatingAlbum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingAlbumRepo extends CrudRepository<UserRatingAlbum, Integer> {
    Optional<UserRatingAlbum> findByUserAndAlbum(int user, int album);
}
