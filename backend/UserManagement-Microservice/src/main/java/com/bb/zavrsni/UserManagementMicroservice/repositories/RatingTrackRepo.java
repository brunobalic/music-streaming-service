package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.UserRatingTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingTrackRepo extends CrudRepository<UserRatingTrack, Integer> {
    Optional<UserRatingTrack> findByUserAndTrack(int user, int track);
}
