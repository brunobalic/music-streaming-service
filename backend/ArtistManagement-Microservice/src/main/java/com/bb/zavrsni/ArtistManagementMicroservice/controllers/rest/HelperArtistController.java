package com.bb.zavrsni.ArtistManagementMicroservice.controllers.rest;

import com.bb.zavrsni.ArtistManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.ArtistManagementMicroservice.models.entities.Artist;
import com.bb.zavrsni.ArtistManagementMicroservice.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/helper/artist")
public class HelperArtistController {

    private final ArtistRepository artistRepository;

    @Autowired
    public HelperArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Artist> helperCreateArtist(
            @RequestPart("artist-name") String artistName) {

        Artist toCreate = new Artist();
        toCreate.setFirst_name(artistName);

        // ovo radim jer auth0Id treba biti unique
        toCreate.setAuth0id(UUID.randomUUID().toString());

        Artist created = artistRepository.save(toCreate);

        // ovo radim da me ne zbune oni random brojevi
        // tako da znam da nemam auth0 upisan...
        created.setAuth0id("auth0 - " + created.getId() + " nema");

        Artist updated = artistRepository.save(created);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updated);
    }

    @RequestMapping(value = "/update/pictureFilename",
            method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Artist> helperUpdateArtistPictureFilename(
            @RequestPart(value = "artist-id") String artistId,
            @RequestPart(value = "picture-filename") String filename) {

        Artist artist = artistRepository
                .findById(Integer.valueOf(artistId))
                .orElseThrow(() -> {
                    throw new UniversalException("Nema artist pod ID: " + artistId);
                });

        artist.setPictureFilename(filename);

        Artist updated = artistRepository.save(artist);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updated);
    }

}
