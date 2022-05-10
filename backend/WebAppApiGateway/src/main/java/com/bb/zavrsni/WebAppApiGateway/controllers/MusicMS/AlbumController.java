package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.getAlbumById(id));
    }

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> getAllAlbumsFromArtist(@PathVariable(value = "artistId") String artistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.getAllPublicAlbumsFromArtist(artistId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAlbumById(@PathVariable String id) {
        albumService.deleteAlbumById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
