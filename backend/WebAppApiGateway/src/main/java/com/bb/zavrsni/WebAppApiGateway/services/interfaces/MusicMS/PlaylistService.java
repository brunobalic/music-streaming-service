package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistDto;

import java.util.List;

public interface PlaylistService {

    PlaylistDto createNewPlaylist(PlaylistCreationDto playlistCreationDto);

    PlaylistDto getPlaylist(String playlistId);

    List<PlaylistDto> getAllUserPlaylists(String userId);

    void deletePlaylist(String playlistId, String userId);

    void addTrack(String playlistId, String trackId);

    void removeTrack(String playlistId, String trackId);

    List<PlaylistDto> searchPlaylistsByName(String query, int page, int size);

}
