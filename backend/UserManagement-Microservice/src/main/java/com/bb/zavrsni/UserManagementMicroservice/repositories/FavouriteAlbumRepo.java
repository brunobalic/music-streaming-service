package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserFavouriteAlbum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouriteAlbumRepo extends CrudRepository<UserFavouriteAlbum, Integer> {
    Optional<UserFavouriteAlbum> findByUserAndAlbum(int user, int album);
}
