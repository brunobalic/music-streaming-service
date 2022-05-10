package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.services.PlayStatsSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class PlayStatsSimulatorController {

    private final PlayStatsSimulator playStatsSimulator;

    @Autowired
    public PlayStatsSimulatorController(PlayStatsSimulator playStatsSimulator) {
        this.playStatsSimulator = playStatsSimulator;
    }

    // todo samo dostupno artistu
    @RequestMapping(value = "/{artistId}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getPlayStatsFromArtist(@PathVariable String artistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getArtistPlayStats(artistId));
    }

    @RequestMapping(value = "/{artistId}/top50",
            method = RequestMethod.GET)
    public ResponseEntity<?> getTopSongsFromArtist(@PathVariable String artistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getTop50FromArtist(artistId));
    }

    @RequestMapping(value = "/global/top100",
            method = RequestMethod.GET)
    public ResponseEntity<?> getGlobalTop100() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playStatsSimulator.getGlobalTop100());
    }

}
