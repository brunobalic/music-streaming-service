package com.bb.zavrsni.MusicManagementMicroservice.utils.mappers;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.GenreDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Genre;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GenreMapper {
    Genre genreDtoToGenre(GenreDto genreDto);

    GenreDto genreToGenreDto(Genre genre);

    List<GenreDto> getGenres(List<Genre> genres);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGenreFromGenreDto(GenreDto genreDto, @MappingTarget Genre genre);
}
