package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.PlaylistCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.PlaylistDto;

import java.util.List;

public interface PlaylistService {

    PlaylistDto createPlaylist(PlaylistCreationDto playlistCreationDto);

    PlaylistDto getPlaylistById(int id);

    List<PlaylistDto> getAllUserPlaylists(int userId);

    void deletePlaylist(int playlistId, String userId);

    PlaylistDto changeName(int playlistId, String newName);

    PlaylistDto changeDescription(int playlistId, String description);

    void addTrack(int playlistId, int trackId);

    void removeTrack(int trackId, int playlistId);

    List<PlaylistDto> searchPlaylists(String searchString, int page, int size);

}
