package com.bb.zavrsni.UserManagementMicroservice.repositories;

import com.bb.zavrsni.UserManagementMicroservice.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByAuth0id(String auth0id);
    Optional<User> findByUsername(String username);
}
