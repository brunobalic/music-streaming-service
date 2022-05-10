package com.bb.zavrsni.WebAppApiGateway.controllers.UserMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.User.RatingDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST - Create new User.
     * @param user User creation data
     * @return Newly created User
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserCreationDto user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createNewUser(user));
    }

    /**
     * Get current user (read from JWT token).
     *
     * @param jwt JWT token
     * @return User object
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByAuth0Id(jwt.getSubject()));
    }

    @RequestMapping(value = "/auth0/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByAuth0Id(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByAuth0Id(id));
    }

    @RequestMapping(value = "/un/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByUsername(username));
    }

    // ADD TO FAVOURITES
    @RequestMapping(value = "/fav/{what}/{what-id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addToFavourites(@AuthenticationPrincipal Jwt jwt,
                                                @PathVariable(name = "what") String what,
                                                @PathVariable(name = "what-id") String whatId) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        userService.addToFavourites(what, String.valueOf(user.getId()), whatId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // REMOVE FROM FAVOURITES
    @RequestMapping(value = "/fav/{what}/{what-id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavourites(@AuthenticationPrincipal Jwt jwt,
                                                     @PathVariable(name = "what") String what,
                                                     @PathVariable(name = "what-id") String whatId) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        userService.removeFromFavourites(what, String.valueOf(user.getId()), whatId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // ADD RATING
    @RequestMapping(value = "/rate/{what}/{what-id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addRating(@AuthenticationPrincipal Jwt jwt,
                                          @PathVariable(name = "what") String what,
                                          @PathVariable(name = "what-id") String whatId,
                                          @RequestBody RatingDto ratingDto) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        userService.addRatingFor(what, String.valueOf(user.getId()), whatId, ratingDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // REMOVE RATING
    @RequestMapping(value = "/rate/{what}/{what-id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeRating(@AuthenticationPrincipal Jwt jwt,
                                             @PathVariable(name = "what") String what,
                                             @PathVariable(name = "what-id") String whatId) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        userService.removeRatingFrom(what, String.valueOf(user.getId()), whatId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
