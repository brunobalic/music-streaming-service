package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.TrackCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tracks")
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * POST - create new track
     *
     * @param trackCreationDto track creation object
     * @return newly created Track
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TrackDto> createTrack(@RequestPart(value = "json") TrackCreationDto trackCreationDto,
                                                @RequestPart(value = "file") ByteArrayResource file,
                                                @RequestPart(value = "file-extension") String fileExtension,
                                                @RequestPart(value = "artistId") String artistId,
                                                @RequestPart(value = "uploaderJwt") String uploaderJwt) {

        System.out.println("trackCreationDto:   " + trackCreationDto);
        System.out.println("fileExtension:      " + fileExtension);
        System.out.println("artistId:           " + artistId);
        System.out.println("uploaderJwt:        " + uploaderJwt);

        System.out.println("trackCreationDto title:   " + trackCreationDto.getTitle());
        System.out.println("trackCreationDto desc:    " + trackCreationDto.getDescription());
        System.out.println("trackCreationDto collId:  " + trackCreationDto.getCollectionId());
        System.out.println("trackCreationDto public:  " + trackCreationDto.isPublicTrack());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trackService.createTrack(trackCreationDto, file, fileExtension, artistId));
    }

    /**
     * GET - track by id
     *
     * @param id track id
     * @return requested Track
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TrackDto> getTrack(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trackService.getTrackById(id));
    }

    /**
     * DELETE - deleted track by id
     *
     * @param id track id
     * @return void
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTrack(@PathVariable int id) {
        trackService.deleteTrack(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * GET - search tracks by name
     *
     * @param query search query string
     * @param page page (optional, default 0)
     * @param size page size (optional, default 10)
     * @return List of Tracks
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<TrackDto>> searchTracks(@RequestParam("q") String query,
                                                       @RequestParam(value = "p", required = false) Optional<Integer> page,
                                                       @RequestParam(value = "s", required = false) Optional<Integer> size) {
        // * Vazno je da primam Integer a ne int !!!
        // * ako param nije poslan onda se resolva u null
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trackService.searchTracks(query, page.orElse(0), size.orElse(10)));
    }

    /**
     * POST - add artist to specific track
     *
     * @param id track id
     * @param artist_id artist is
     * @return void
     */
    @RequestMapping(value = "/{id}/artist/{artist_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrackArtist(@PathVariable(name = "id") int id,
                                               @PathVariable(name = "artist_id") int artist_id) {
        trackService.addArtist(id, artist_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * POST - add genre to track
     *
     * @param id track id
     * @param genre_id genre id
     * @return void
     */
    @RequestMapping(value = "/{id}/genre/{genre_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addTrackGenre(@PathVariable(name = "id") int id,
                                              @PathVariable(name = "genre_id") int genre_id) {
        trackService.addGenre(id, genre_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * POST - increment (+1) play count for specific track
     *
     * @param id track id
     * @return void
     */
    @RequestMapping(value = "/{id}/counter-plus", method = RequestMethod.POST)
    public ResponseEntity<Void> incrementPlayCount(@PathVariable(name = "id") int id) {
        trackService.incrementPlayCount(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}