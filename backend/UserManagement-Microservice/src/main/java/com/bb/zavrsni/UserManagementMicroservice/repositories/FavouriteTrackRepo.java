package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserFavouriteTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouriteTrackRepo extends CrudRepository<UserFavouriteTrack, Integer> {
    Optional<UserFavouriteTrack> findByUserAndTrack(int user, int track);
}
