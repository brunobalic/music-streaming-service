package com.bb.zavrsni.ArtistManagementMicroservice.controllers.rest;

import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistCreation;
import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistDto;
import com.bb.zavrsni.ArtistManagementMicroservice.services.interfaces.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * Create new Artist.
     *
     * @param newArtist Artist data
     * @return Newly created Artist
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ArtistDto> createNewArtist(@RequestBody ArtistCreation newArtist) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistService.createArtist(newArtist));
    }

    /**
     * Get Artist by ID.
     *
     * @param id Artist ID
     * @return Requested Artist
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtistById(id));
    }

    /**
     * Get Artist by Auth0 ID.
     *
     * @param id Auth0 ID
     * @return Requested Artist
     */
    @RequestMapping(value = "/auth0", method = RequestMethod.GET)
    public ResponseEntity<ArtistDto> getArtistByAuth0Id(@RequestParam("id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtistByAuth0id(id));
    }

    /**
     * Search Artists.
     *
     * @param query String to search
     * @param page page
     * @param size page size
     * @return List of Artists
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistDto>> searchArtists(
            @RequestParam("q") String query,
            @RequestParam(value = "p", required = false) Optional<Integer> page, // ako ga nema bit ce null, vazno da je Integer
            @RequestParam(value = "s", required = false) Optional<Integer> size) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.searchArtistsByName(query, page.orElse(0), size.orElse(5)));
    }

}
