package com.bb.zavrsni.MusicManagementMicroservice.controllers.rest;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.GenreDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.impl.GenreServiceImpl;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * GET - all genres
     *
     * @return List of all genres
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(genreService.getAllGenres());
    }

}
