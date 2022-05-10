package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();

}
