package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();

}
