package com.bb.zavrsni.MusicManagementMicroservice.services.impl;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Playlist;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.TrackPlaylist;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.PlaylistDto;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.PlaylistRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.TrackPlaylistRepository;
import com.bb.zavrsni.MusicManagementMicroservice.models.dto.PlaylistCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.PlaylistService;
import com.bb.zavrsni.MusicManagementMicroservice.utils.mappers.PlaylistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TrackPlaylistRepository trackPlaylistRepository;
    private final PlaylistMapper playlistMapper;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, TrackPlaylistRepository trackPlaylistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.trackPlaylistRepository = trackPlaylistRepository;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public PlaylistDto createPlaylist(PlaylistCreationDto playlistCreationDto) {
        Playlist toCreate = playlistMapper.playlistCreationDtoToPlaylist(playlistCreationDto);
        Playlist created = playlistRepository.save(toCreate);
        return playlistMapper.playlistToPlaylistDto(created);
    }

    @Override
    public PlaylistDto getPlaylistById(int id) {
        Playlist playlist = playlistRepository
                .findById(id)
                .orElseThrow(() -> new UniversalException(String.format("Playlist with id: %s does not exist!", id)));
        return playlistMapper.playlistToPlaylistDto(playlist);
    }

    @Override
    public List<PlaylistDto> getAllUserPlaylists(int userId) {
        return playlistMapper.getPlaylists(playlistRepository.findAllByCreatorId(userId));
    }

    @Override
    public void deletePlaylist(int playlistId, String userId) {
        if (getAllUserPlaylists(Integer.parseInt(userId)).stream().noneMatch(elem -> elem.getId() == playlistId)) {
            throw new UniversalException(String.format("Cannot delete playlist - playlist with id: %s was not created by you!", playlistId));
        }
        Playlist toDelete = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new UniversalException(String.format("Cannot delete playlist - playlist with id: %s does not exist!", playlistId)));
        playlistRepository.delete(toDelete);
    }

    @Override
    public PlaylistDto changeName(int playlistId, String newName) {
        Playlist playlist = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new UniversalException(String.format("Cannot change playlist name - playlist with id: %s does not exist!", playlistId)));
        playlist.setName(newName);
        Playlist updated = playlistRepository.save(playlist);
        return playlistMapper.playlistToPlaylistDto(updated);
    }

    @Override
    public PlaylistDto changeDescription(int playlistId, String description) {
        Playlist playlist = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new UniversalException(String.format("Cannot change playlist description - playlist with id: %s does not exist!", playlistId)));
        playlist.setDescription(description);
        Playlist updated = playlistRepository.save(playlist);
        return playlistMapper.playlistToPlaylistDto(updated);
    }

    @Override
    public void addTrack(int playlistId, int trackId) {
        Playlist pl = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new UniversalException("Playlist does not exist!"));
        trackPlaylistRepository.save(new TrackPlaylist(trackId, playlistId));
        pl.setNumber_of_tracks(pl.getNumber_of_tracks() + 1);
        playlistRepository.save(pl);
    }

    @Override
    public void removeTrack(int trackId, int playlistId) {
        Playlist pl = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new UniversalException("Playlist does not exist!"));
        trackPlaylistRepository.delete(
                trackPlaylistRepository
                        .findByTrackAndPlaylist(trackId, playlistId)
                        .orElseThrow(() -> new UniversalException(String.format("Cannot remove track (id: %s) from playlist (id: %s) - track is not on playlist!", trackId, playlistId)))
        );
        pl.setNumber_of_tracks(pl.getNumber_of_tracks() - 1);
        playlistRepository.save(pl);
    }

    @Override
    public List<PlaylistDto> searchPlaylists(String searchString, int page, int size) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 5;
        Pageable pageable = PageRequest.of(page, size);
        return playlistMapper.getPlaylists(
                playlistRepository.searchPlaylists(searchString.toLowerCase(), pageable)
        );
    }

}
