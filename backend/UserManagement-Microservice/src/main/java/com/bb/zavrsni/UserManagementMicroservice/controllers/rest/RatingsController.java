package com.bb.zavrsni.UserManagementMicroservice.controllers.rest;

import com.bb.zavrsni.UserManagementMicroservice.models.dto.RatingDto;
import com.bb.zavrsni.UserManagementMicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ratings")
public class RatingsController {

    private final UserService userService;

    @Autowired
    public RatingsController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add Album Rating.
     * @param userId User ID
     * @param albumId Album ID
     * @param ratingDto Rating data
     * @return Void
     */
    @RequestMapping(value = "/albums",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addRatingToAlbum(@RequestPart(name = "user-id") String userId,
                                                 @RequestPart(name = "album-id") String albumId,
                                                 @RequestPart(name = "rating-dto") RatingDto ratingDto) {

        userService.addRatingToAlbum(userId, albumId, ratingDto.getScore(), ratingDto.getComment());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Album Rating.
     *
     * @param userId User ID
     * @param albumId Album ID
     * @return Void
     */
    @RequestMapping(value = "/albums",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeRatingFromAlbum(@RequestParam(name = "user-id") String userId,
                                                      @RequestParam(name = "album-id") String albumId) {

        userService.removeRatingFromAlbum(userId, albumId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Add Track Rating.
     *
     * @param userId User ID
     * @param trackId Track ID
     * @param ratingDto Rating data
     * @return Void
     */
    @RequestMapping(value = "/tracks",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addRatingToTrack(@RequestPart(name = "user-id") String userId,
                                                 @RequestPart(name = "track-id") String trackId,
                                                 @RequestPart(name = "rating-dto") RatingDto ratingDto) {

        userService.addRatingToTrack(userId, trackId, ratingDto.getScore(), ratingDto.getComment());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Track Rating.
     *
     * @param userId User ID
     * @param trackId Track ID
     * @return Void
     */
    @RequestMapping(value = "/tracks",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeRatingFromTrack(@RequestParam(name = "user-id") String userId,
                                                      @RequestParam(name = "track-id") String trackId) {

        userService.removeRatingFromTrack(userId, trackId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
