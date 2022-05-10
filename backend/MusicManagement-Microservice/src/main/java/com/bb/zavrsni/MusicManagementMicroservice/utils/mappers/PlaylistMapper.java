package com.bb.zavrsni.MusicManagementMicroservice.utils.mappers;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.PlaylistCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Playlist;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.PlaylistDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlaylistMapper {
    Playlist playlistDtoToPlaylist(PlaylistDto playlistDto);

    PlaylistDto playlistToPlaylistDto(Playlist playlist);

    List<PlaylistDto> getPlaylists(List<Playlist> playlists);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "publish_date", ignore = true)
    @Mapping(target = "number_of_tracks", ignore = true)
    Playlist playlistCreationDtoToPlaylist(PlaylistCreationDto playlistCreationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlaylistFromPlaylistDto(PlaylistDto playlistDto, @MappingTarget Playlist playlist);
}
