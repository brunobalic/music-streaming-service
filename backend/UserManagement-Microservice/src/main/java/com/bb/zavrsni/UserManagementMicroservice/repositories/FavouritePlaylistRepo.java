package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserFavouritePlaylist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouritePlaylistRepo extends CrudRepository<UserFavouritePlaylist, Integer> {
    Optional<UserFavouritePlaylist> findByUserAndPlaylist(int user, int playlist);
}
