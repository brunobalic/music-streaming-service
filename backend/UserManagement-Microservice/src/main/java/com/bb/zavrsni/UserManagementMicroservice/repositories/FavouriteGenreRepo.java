package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserFavouriteGenre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouriteGenreRepo extends CrudRepository<UserFavouriteGenre, Integer> {
    Optional<UserFavouriteGenre> findByUserAndGenre(int user, int genre);
}
