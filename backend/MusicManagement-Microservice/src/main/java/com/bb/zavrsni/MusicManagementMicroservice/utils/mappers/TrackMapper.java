package com.bb.zavrsni.MusicManagementMicroservice.utils.mappers;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.TrackCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Track;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TrackMapper {
    Track trackDtoToTrack(TrackDto trackDto);

    TrackDto trackToTrackDto(Track track);

    List<TrackDto> trackListToTrackDtoList(List<Track> tracks);

    @Mapping(target = "albumData", ignore = true)
    @Mapping(target = "album_id", source = "collectionId")
    @Mapping(target = "public_track", source = "publicTrack")
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "duration_sec", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "artist_id", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artistObj", ignore = true)
    @Mapping(target = "publish_timestamp", ignore = true)
    @Mapping(target = "publish_at", ignore = true)
    @Mapping(target = "play_count", ignore = true)
    @Mapping(target = "last_played", ignore = true)
    @Mapping(target = "filename", ignore = true)
    @Mapping(target = "trackPosition", ignore = true)
    Track trackCreationDtoToTrack(TrackCreationDto trackCreationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTrackFromTrackDto(TrackDto trackDto, @MappingTarget Track track);
}
