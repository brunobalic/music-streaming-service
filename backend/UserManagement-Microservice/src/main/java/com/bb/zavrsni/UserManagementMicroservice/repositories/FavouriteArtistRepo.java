package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserFavouriteArtist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouriteArtistRepo extends CrudRepository<UserFavouriteArtist, Integer> {
    Optional<UserFavouriteArtist> findByUserAndArtist(int user, int artist);
}
