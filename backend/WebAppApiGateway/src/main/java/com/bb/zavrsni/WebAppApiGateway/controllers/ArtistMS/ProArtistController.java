package com.bb.zavrsni.WebAppApiGateway.controllers.ArtistMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.CollectionCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AlbumService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pro/artist")
public class ProArtistController {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final TrackService trackService;

    @Autowired
    public ProArtistController(ArtistService artistService, AlbumService albumService, TrackService trackService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.trackService = trackService;
    }

    /**
     * GET - Current logged in artist data.
     *
     * @param jwt JWT token
     * @return Artist object
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ArtistDto> getCurrentArtist(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtistByAuth0Id(jwt.getSubject()));
    }

    /**
     * GET - Get all Albums from currently logged in Artist.
     *
     * @param jwt JWT token
     * @return List of Albums
     */
    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> getCurrentArtistAlbums(@AuthenticationPrincipal Jwt jwt) {
        String id = artistService.getArtistByAuth0Id(jwt.getSubject()).getId().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(albumService.getAllAlbumsFromArtist(id));
    }

    /**
     * POST - Create new collection/album.
     *
     * @param collectionCreationDto Collection data
     * @param file Image file
     * @param jwt JWT token
     * @return Newly created Album object
     */
    @RequestMapping(value = "/albums",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumDto> createNewCollection(
            @RequestPart(value = "key-data") CollectionCreationDto collectionCreationDto,
            @RequestPart(value = "key-file") ByteArrayResource file,
            @AuthenticationPrincipal Jwt jwt) {

        String artistId = artistService.getArtistByAuth0Id(jwt.getSubject()).getId().toString();
        AlbumDto created = albumService.createNewAlbum(collectionCreationDto, file, artistId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /**
     * POST - Upload new track.
     *
     * @param trackCreationDto Track data
     * @param audioFile Audio file
     * @param jwt JWT token
     * @return Newly created TrackDto
     */
    @RequestMapping(value = "/tracks",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TrackDto> uploadNewTrack(
            @RequestPart(value = "data") TrackCreationDto trackCreationDto,
            @RequestPart(value = "file") ByteArrayResource audioFile,
            @AuthenticationPrincipal Jwt jwt) {

        // todo - provjera da audio file nije null !

        System.out.println("++++");
        System.out.println(trackCreationDto.getTitle());
        System.out.println(trackCreationDto.getCollectionId());
//
//        return ResponseEntity.status(200).build();

        String artistId = artistService.getArtistByAuth0Id(jwt.getSubject()).getId().toString();
        TrackDto track =  trackService.uploadNewTrack(trackCreationDto, audioFile, artistId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(track);
    }

}
