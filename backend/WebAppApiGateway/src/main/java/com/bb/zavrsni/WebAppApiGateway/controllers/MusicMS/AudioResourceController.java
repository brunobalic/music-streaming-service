package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AudioResourceService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/media/audio")
public class AudioResourceController {

    private final AudioResourceService audioResourceService;
    private final ArtistService artistService;
    private final UserService userService;

    @Autowired
    public AudioResourceController(AudioResourceService audioResourceService, ArtistService artistService, UserService userService) {
        this.audioResourceService = audioResourceService;
        this.artistService = artistService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{trackId}",
            method = RequestMethod.GET,
            produces = "audio/mpeg") // todo trebalo bi prelagodit prema file-u, kada je npr aac...
    public ResponseEntity<byte[]> getAudioResource(@RequestHeader(name = "Range", required = false) String rangeHeader, // todo - trenutno nije required
                                                   //@RequestHeader(name = "Range", defaultValue = "0-1048576") String rangeBytes, //
                                                   @PathVariable String trackId,
                                                   @AuthenticationPrincipal Jwt jwt) {
        List<String> role = jwt.getClaimAsStringList("http://localhost:8080/roles");
        System.out.println("role from jwt: " + role.get(0));

        if (role.get(0).equals("ROLE_ARTIST")) {
            String artistId = artistService.getArtistByAuth0Id(jwt.getSubject()).getId().toString();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(audioResourceService.getAudioResourceArtistRequest(trackId, artistId));
        }

        if (role.get(0).equals("ROLE_REGULAR-USER")) {
            String userId = userService.getUserByAuth0Id(jwt.getSubject()).getId().toString();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(audioResourceService.getAudioResource(trackId, userId));
        }

        return ResponseEntity.badRequest().build();
    }

}
