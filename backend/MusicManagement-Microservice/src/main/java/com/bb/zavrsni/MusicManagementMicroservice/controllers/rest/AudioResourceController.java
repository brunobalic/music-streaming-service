package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.services.AudioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Kontroler preko kojeg se dohvaca audio file.
 */
@RestController
@RequestMapping(value = "/media/audio")
public class AudioResourceController {

    private final AudioServiceImpl audioService;

    @Autowired
    public AudioResourceController(AudioServiceImpl audioService) {
        this.audioService = audioService;
    }

    @RequestMapping(value = "/{trackId}",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = "audio/mpeg") // je ovo
    public ResponseEntity<byte[]> getAudioFile(
            @PathVariable String trackId,
            @RequestPart("userId") String userId) {
        // umjesto da posaljem samo user id, mogu poslati userov jwt token pa od tu procitat sve sta mi treba

        System.out.println(trackId);
        System.out.println(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioService.getAudioResource(trackId, userId));
    }

    // test preko http GET
    @RequestMapping(value = "/{trackId}",
            method = RequestMethod.GET,
            produces = "audio/mpeg") // je ovo
    public ResponseEntity<byte[]> getAudioFileTEST(
            @PathVariable String trackId,
            @RequestHeader(value = "app-userId") String userId) {

        System.out.println(trackId);
        System.out.println(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioService.getAudioResource(trackId, userId));
    }

    @RequestMapping(value = "/{trackId}/increment",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> incrementPlayCountForTrack(
            @PathVariable String trackId,
            @RequestPart("userId") String userId) {

        audioService.incrementPlayCount(userId, new Date().getTime());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

}
