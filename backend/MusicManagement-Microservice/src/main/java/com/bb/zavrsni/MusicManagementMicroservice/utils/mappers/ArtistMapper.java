package com.bb.zavrsni.MusicManagementMicroservice.utils.mappers;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.ArtistDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Artist;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ArtistMapper {
    Artist artistDtoToArtist(ArtistDto artistDto);

    ArtistDto artistToArtistDto(Artist artist);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArtistFromArtistDto(ArtistDto artistDto, @MappingTarget Artist artist);
}
