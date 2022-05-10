package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tracks")
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TrackDto> getTrack(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trackService.getTrack(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTrack(@PathVariable String id) {
        trackService.deleteTrack(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RequestMapping(value = "/{trackId}/artists/{artistId}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrackArtist(@PathVariable(name = "trackId") String trackId,
                                               @PathVariable(name = "artistId") String artistId) {

        trackService.addTrackArtist(String.valueOf(trackId), String.valueOf(artistId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RequestMapping(value = "/{trackId}/genres/{genreId}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrackGenre(@PathVariable(name = "trackId") String trackId,
                                              @PathVariable(name = "genreId") String genreId) {

        trackService.addTrackGenre(trackId, genreId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
