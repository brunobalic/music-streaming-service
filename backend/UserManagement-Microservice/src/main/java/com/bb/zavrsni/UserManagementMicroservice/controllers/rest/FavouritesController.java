package com.bb.zavrsni.UserManagementMicroservice.controllers.rest;

import com.bb.zavrsni.UserManagementMicroservice.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fav")
public class FavouritesController {

    private final UserService userService;

    @Autowired
    public FavouritesController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add Album with specified ID to Users <i>favourite albums</i> list.
     *
     * @param userId User ID
     * @param albumId Album ID
     * @return Void
     */
    @RequestMapping(value = "/albums",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addToFavouriteAlbums(@RequestPart(name = "user-id") String userId,
                                                     @RequestPart(name = "album-id") String albumId) {

        userService.addToFavouriteAlbums(userId, albumId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Album with specified ID from Users <i>favourite albums</i> list.
     *
     * @param userId User ID
     * @param albumId Album ID
     * @return Void
     */
    @RequestMapping(value = "/albums",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavouriteAlbums(@RequestParam(name = "user-id") String userId,
                                                          @RequestParam(name = "album-id") String albumId) {

        userService.removeFromFavouriteAlbums(userId, albumId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Add Artist with specified ID to Users <i>favourite artists</i> list.
     *
     * @param userId User ID
     * @param artistId Artist ID
     * @return Void
     */
    @RequestMapping(value = "/artists",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addToFavouriteArtists(@RequestPart(name = "user-id") String userId,
                                                      @RequestPart(name = "artist-id") String artistId) {

        userService.addToFavouriteArtists(userId, artistId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Artist with specified ID from Users <i>favourite artists</i> list.
     *
     * @param userId User ID
     * @param artistId Artist ID
     * @return Void
     */
    @RequestMapping(value = "/artists",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavouriteArtists(@RequestParam(name = "user-id") String userId,
                                                           @RequestParam(name = "artist-id") String artistId) {

        userService.removeFromFavouriteArtists(userId, artistId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Add Genre with specified ID to Users <i>favourite genres</i> list.
     *
     * @param userId User ID
     * @param genreId Genre ID
     * @return Void
     */
    @RequestMapping(value = "/genres",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addToFavouriteGenres(@RequestPart(name = "user-id") String userId,
                                                     @RequestPart(name = "genre-id") String genreId) {

        userService.addToFavouriteGenre(userId, genreId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Genre with specified ID from Users <i>favourite genres</i> list.
     *
     * @param userId User ID
     * @param genreId Genre ID
     * @return Void
     */
    @RequestMapping(value = "/genres",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavouriteGenres(@RequestParam(name = "user-id") String userId,
                                                          @RequestParam(name = "genre-id") String genreId) {

        userService.removeFromFavouriteGenre(userId, genreId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Add Playlist with specified ID to Users <i>favourite playlists</i> list.
     *
     * @param userId User ID
     * @param playlistId Playlist ID
     * @return Void
     */
    @RequestMapping(value = "/playlists",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addToFavouritePlaylist(@RequestPart(name = "user-id") String userId,
                                                       @RequestPart(name = "playlist-id") String playlistId) {

        userService.addToFavouritePlaylist(userId, playlistId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Playlist with specified ID from Users <i>favourite playlists</i> list.
     *
     * @param userId User ID
     * @param playlistId Playlist ID
     * @return Void
     */
    @RequestMapping(value = "/playlists",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavouritePlaylist(@RequestParam(name = "user-id") String userId,
                                                            @RequestParam(name = "playlist-id") String playlistId) {

        userService.removeFromFavouritePlaylist(userId, playlistId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Add Track with specified ID to Users <i>favourite tracks</i> list.
     *
     * @param userId User ID
     * @param trackId Track ID
     * @return Void
     */
    @RequestMapping(value = "/tracks",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addToFavouriteTrack(@RequestPart(name = "user-id") String userId,
                                                    @RequestPart(name = "track-id") String trackId) {

        userService.addToFavouriteTrack(userId, trackId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Delete Track with specified ID from Users <i>favourite tracks</i> list.
     *
     * @param userId User ID
     * @param trackId Track ID
     * @return Void
     */
    @RequestMapping(value = "/tracks",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeFromFavouriteTrack(@RequestParam(name = "user-id") String userId,
                                                         @RequestParam(name = "track-id") String trackId) {

        userService.removeFromFavouriteTrack(userId, trackId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
