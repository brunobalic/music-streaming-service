package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.PlaylistCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.PlaylistDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /**
     * POST - create new playlist
     *
     * @param playlistCreationDto PlaylistCreationDto object
     * @return newly created playlist
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<PlaylistDto> createNewPlaylist(@RequestBody PlaylistCreationDto playlistCreationDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.createPlaylist(playlistCreationDto));
    }

    /**
     * GET - playlist by id
     *
     * @param id playlist id
     * @return requested playlist
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.getPlaylistById(id));
    }

    /**
     * GET - all playlists from specific user
     *
     * @param userId user id
     * @return list of playlists
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<PlaylistDto>> getAllUserPlaylists(@PathVariable(value = "id") int userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.getAllUserPlaylists(userId));
    }

    /**
     * DELETE - playlist
     * @param id playlist id
     * @return void
     */
    @RequestMapping(value = "/{id}/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id, @PathVariable String userId) {
        playlistService.deletePlaylist(Integer.parseInt(id), userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * GET - search playlists by name
     *
     * @param query query string
     * @param page page
     * @param size page size
     * @return list of playlists
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<PlaylistDto>> searchPlaylists(
            @RequestParam("q") String query,
            @RequestParam(value = "p", required = false) Optional<Integer> page, // ako ga nema bit ce null, vazno da je Integer
            @RequestParam(value = "s", required = false) Optional<Integer> size) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.searchPlaylists(query, page.orElse(0), size.orElse(5)));
    }

    // todo
    /*
    // vidit kako cu poslat samo Sytring kao novo ime
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Void> changePlaylistName() {
        playlistService.changeName();
        return ResponseEntity.status(200).build();
    }

    // vidit kako cu poslat samo Sytring kao novo ime
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Void> changePlaylistDescription() {
        playlistService.changeDescription();
        return ResponseEntity.status(200).build();
    }
     */

    /**
     * POST - add track to playlist
     *
     * @param playlist_id playlist id
     * @param track_id track id
     * @return void
     */
    @RequestMapping(value = "/{id}/track/{track_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrack(@PathVariable(name = "id") int playlist_id,
                                         @PathVariable(name = "track_id") int track_id) {

        playlistService.addTrack(playlist_id, track_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * DELETE - remove track from playlist
     *
     * @param playlist_id playlist id
     * @param track_id track id
     * @return void
     */
    @RequestMapping(value = "/{id}/track/{track_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeTrack(@PathVariable(name = "id") int playlist_id,
                                            @PathVariable(name = "track_id") int track_id) {

        playlistService.removeTrack(track_id, playlist_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
