package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.TrackCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TrackService {

    TrackDto createTrack(TrackCreationDto trackCreationDto, ByteArrayResource file, String fileExtension, String uploaderId);

    TrackDto getTrackById(int id);

    void deleteTrack(int trackId);

    void addArtist(int trackId, int artistId);

    void addGenre(int trackId, int genreId);

    List<TrackDto> searchTracks(String searchString, int page, int size);

    void incrementPlayCount(int trackId);

}
