package com.bb.zavrsni.MusicManagementMicroservice.utils.mappers;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.CollectionCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Album;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.AlbumDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AlbumMapper {
    Album albumDtoToAlbum(AlbumDto albumDto);

    AlbumDto albumToAlbumDto(Album album);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "collectionType", target = "collection_type")
    @Mapping(source = "releaseDate", target = "official_release_date")
    @Mapping(source = "publishDate", target = "publish_date")
    @Mapping(source = "publishTime", target = "publish_time")
    @Mapping(source = "artistId", target = "artist_id")
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "number_of_tracks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicVisible", ignore = true)
    @Mapping(target = "filename", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "artistObj", ignore = true)
    Album map(CollectionCreationDto object);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAlbumFromAlbumDto(AlbumDto albumDto, @MappingTarget Album album);
}
