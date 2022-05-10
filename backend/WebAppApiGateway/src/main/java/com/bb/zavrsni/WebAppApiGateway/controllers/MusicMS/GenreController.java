package com.bb.zavrsni.WebAppApiGateway.controllers.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.GenreDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.GenreService;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(genreService.getAllGenres());
    }

}
