package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.AlbumDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * POST - create new album/collection
     *
     * @param data json file
     * @param file artwork image file
     * @param fileExtension image extension
     * @param artistId artist/creator id
     * @return AlbumDto object
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumDto> createNewCollection(
            @RequestPart(value = "data") ByteArrayResource data,
            @RequestPart(value = "file") ByteArrayResource file,
            @RequestPart(value = "file-extension") String fileExtension,
            @RequestPart(value = "key-artistId") String artistId) {

        AlbumDto albumDto = albumService.createAlbum(data, artistId, file, fileExtension);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(albumDto);
    }

    /**
     * GET - album by id
     *
     * @param id album id
     * @return AlbumDto object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.getAlbumById(id));
    }

    /**
     * DELETE - album by id
     *
     * @param id album id
     * @return void
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAlbumById(@PathVariable int id) {
        albumService.deleteAlbum(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * GET - all albums from specific artist
     *
     * @param id artist id
     * @return List of AlbumDto objects
     */
    @RequestMapping(value = "/artist/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> getAllAlbumsFromArtist(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.getAllAlbumsFromArtist(id));
    }

    /**
     * GET - search for albums by name
     *
     * @param query search string
     * @param page page
     * @param size page size (items per page)
     * @return List of AlbumDto objects
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> searchAlbums(
            @RequestParam("q") String query,
            @RequestParam(value = "p", required = false) Optional<Integer> page,
            @RequestParam(value = "s", required = false) Optional<Integer> size) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.searchAlbums(query, page.orElse(0), size.orElse(5)));
    }


    // TODO
    /*

    // ADD TRACKS TO ALBUM
    @RequestMapping(value = "/{id}/tracks", method = RequestMethod.POST)
    public ResponseEntity<Void> addTracks() {

    }

    // ADD ARTISTS TO ALBUM
    @RequestMapping(value = "/{id}/artists", method = RequestMethod.POST)
    public ResponseEntity<Void> addArtists() {

    }

    // ADD GENRES TO ALBUM
    @RequestMapping(value = "/{id}/genres", method = RequestMethod.POST)
    public ResponseEntity<Void> addGenres() {

    }

    // REMOVE GENRES FROM ALBUM
    @RequestMapping(value = "/{id}/genres", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeGenres(@PathVariable(name = "id") int albumId, @RequestBody) int genreId) {
        albumService.removeGenres(albumId, );
        return ResponseEntity.status(200).build();
    }

    
     */

}
