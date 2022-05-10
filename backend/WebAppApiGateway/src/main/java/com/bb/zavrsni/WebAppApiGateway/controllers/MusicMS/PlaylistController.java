package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.PlaylistService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<PlaylistDto> createNewPlaylist(@AuthenticationPrincipal Jwt jwt,
                                                         @RequestBody PlaylistCreationDto playlistCreation) {

        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        playlistCreation.setCreator_id(user.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.createNewPlaylist(playlistCreation));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.getPlaylist(id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<PlaylistDto>> getAllPlaylistsFromCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.getAllUserPlaylists(String.valueOf(user.getId())));
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<PlaylistDto>> getAllPlaylistsFromSpecificUser(@PathVariable(value = "userId") String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.getAllUserPlaylists(userId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        UserDto user = userService.getUserByAuth0Id(jwt.getSubject());
        playlistService.deletePlaylist(id, user.getId().toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RequestMapping(value = "/{id}/tracks/{trackId}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrackToPlaylist(@PathVariable(name = "id") String playlistId,
                                                   @PathVariable(name = "trackId") String trackId) {
        playlistService.addTrack(playlistId, trackId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RequestMapping(value = "/{id}/tracks/{track_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeTrackFromPlaylist(@PathVariable(name = "id") String playlist_id,
                                                        @PathVariable(name = "track_id") String track_id) {
        playlistService.removeTrack(playlist_id, track_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
