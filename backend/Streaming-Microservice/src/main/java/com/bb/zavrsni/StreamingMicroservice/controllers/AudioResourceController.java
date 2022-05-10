package com.bb.zavrsni.StreamingMicroservice.controllers;

import com.bb.zavrsni.StreamingMicroservice.services.interfaces.AudioResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/media/audio")
public class AudioResourceController {

    private final AudioResourceService audioResourceService;

    @Autowired
    public AudioResourceController(AudioResourceService audioResourceService) {
        this.audioResourceService = audioResourceService;
    }

    /**
     * POST - upload audio file
     *
     * ime audio file-a je:
     *      id_<tract_id>.mp3
     *
     * @param file ByteArrayResource
     * @param trackId track ID
     * @return name of uploaded resource
     */
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadAudioFile(
            @RequestPart(value = "file") ByteArrayResource file,
            @RequestPart(value = "file-extension") String fileExtension,
            @RequestPart(value = "id") String trackId) {

        String filename = audioResourceService.saveAudioResource(file, fileExtension, trackId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filename);
    }

    /**
     * GET - audio file with specified filename
     * <br>
     * Ime audio file-a je: id_<tract_id>.mp3
     *
     * @param fileName file name
     * @return audio file
     */
    @RequestMapping(value = "/{fileName}",
            method = RequestMethod.GET,
            produces = "audio/mpeg")
    public ResponseEntity<FileSystemResource> getAudioFile(@PathVariable String fileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioResourceService.getAudioResource(fileName));
    }

}
