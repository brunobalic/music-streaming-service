package com.bb.zavrsni.UserManagementMicroservice.controllers.rest;

import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserCreation;
import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserDto;
import com.bb.zavrsni.UserManagementMicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create new User.
     *
     * @param user User data
     * @return Newly created User resource
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserCreation user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.createNewUser(user));
    }

    /**
     * Get User by ID.
     *
     * @param id User ID
     * @return User resource
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    /**
     * Get User by Auth0 ID.
     *
     * @param id Auth0 ID
     * @return User resource
     */
    @RequestMapping(value = "/auth0", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByAuth0Id(@RequestParam("id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByAuth0Id(id));
    }

    /**
     * Get User by username.
     *
     * @param username Users username
     * @return User resource
     */
    @RequestMapping(value = "/un/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByUsername(username));
    }

}
