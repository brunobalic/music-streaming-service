package com.bb.zavrsni.StreamingMicroservice.controllers;

import com.bb.zavrsni.StreamingMicroservice.services.interfaces.ImageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/media/image")
public class ImageResourceController {

    private final ImageResourceService imageResourceService;

    @Autowired
    public ImageResourceController(ImageResourceService imageResourceService) {
        this.imageResourceService = imageResourceService;
    }

    /**
     * POST - upload image
     *
     * @param file file resource
     * @param type image for what type of content('album', 'playlist', 'artist')
     * @param id id of content
     * @return name of stored image resource
     */
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImageResource(@RequestPart(value = "key-file") ByteArrayResource file,
                                                @RequestPart(value = "key-file-extension") String fileExtension,
                                                @RequestPart(value = "key-type") String type,
                                                @RequestPart(value = "key-id") String id) {

        String filename = imageResourceService.saveImageResource(file, fileExtension, type, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filename);
    }

    /**
     * GET - image with specified file name.
     *
     * @param filename file name
     * @return image resource as byte array
     */
    @RequestMapping(value = "/{filename}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageResource(@PathVariable String filename) {
        byte[] image = imageResourceService.getImageResource(filename);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .cacheControl(CacheControl.maxAge(6, TimeUnit.HOURS))
                .body(image);
    }

}
