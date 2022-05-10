package com.bb.zavrsni.MusicManagementMicroservice.services.impl;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.GenreDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Genre;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.GenreRepository;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.GenreService;
import com.bb.zavrsni.MusicManagementMicroservice.utils.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        Iterable<Genre> x = genreRepository.findAll();
        List<Genre> genres = StreamSupport.stream(x.spliterator(), false).collect(Collectors.toList());
        return genreMapper.getGenres(genres);
    }

}
