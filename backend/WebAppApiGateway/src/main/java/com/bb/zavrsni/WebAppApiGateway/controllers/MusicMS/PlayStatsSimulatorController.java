package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement.PlayStatsSimulator;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music/stats")
public class PlayStatsSimulatorController {

    private final PlayStatsSimulator playStatsSimulator;
    private final ArtistService artistService;

    @Autowired
    public PlayStatsSimulatorController(PlayStatsSimulator playStatsSimulator, ArtistService artistService) {
        this.playStatsSimulator = playStatsSimulator;
        this.artistService = artistService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET)
    public ResponseEntity<?> getPlayStatsFromArtist(@AuthenticationPrincipal Jwt jwt) {
        ArtistDto artist = artistService.getArtistByAuth0Id(jwt.getSubject());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getArtistPlayStats(String.valueOf(artist.getId())));
    }

    @RequestMapping(value = "/top50/{artistId}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getTop50FromArtist(@PathVariable String artistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getTop50FromArtist(artistId));
    }

    @RequestMapping(value = "/global/top100",
            method = RequestMethod.GET)
    public ResponseEntity<?> getGlobalTop100() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getGloablTop100());
    }

}
