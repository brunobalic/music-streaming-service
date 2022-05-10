package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.services.AudioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/play-history")
public class UserPlayHistory {

    private final AudioServiceImpl audioService;

    @Autowired

    public UserPlayHistory(AudioServiceImpl audioService) {
        this.audioService = audioService;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserPlayHistory(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(audioService.getUserPlayHistory(userId));
    }

}
