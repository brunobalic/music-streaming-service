package com.bb.zavrsni.ArtistManagementMicroservice.utils.mappers;

import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistDto;
import com.bb.zavrsni.ArtistManagementMicroservice.models.entities.Artist;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ArtistMapper {
    Artist artistDtoToArtist(ArtistDto artistDto);

    ArtistDto artistToArtistDto(Artist artist);

    List<ArtistDto> artistToArtistDto(List<Artist> artistList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArtistFromArtistDto(ArtistDto artistDto, @MappingTarget Artist artist);
}
