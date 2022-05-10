package com.bb.zavrsni.WebAppApiGateway.controllers.ArtistMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * POST - Create new Artist.
     *
     * @param newArtist Artist data
     * @return Newly created Artist
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ArtistDto> createNewArtist(@RequestBody ArtistCreationDto newArtist) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistService.createNewArtist(newArtist));
    }

    /**
     * GET - Artist by ID.
     *
     * @param id Artist ID
     * @return Artist object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtist(String.valueOf(id)));
    }

    /**
     * GET - Artist by Auth0 ID.
     *
     * @param id Artist Auth0 ID
     * @return Artist object
     */
    @RequestMapping(value = "/auth0/{id}", method = RequestMethod.GET)
    public ResponseEntity<ArtistDto> getArtistByAuth0Id(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtistByAuth0Id(id));
    }

}
