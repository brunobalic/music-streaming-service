package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TrackService {

    TrackDto uploadNewTrack(TrackCreationDto trackCreationDto, ByteArrayResource audioFile, String artistId);

    TrackDto getTrack(String trackId);

    void deleteTrack(String trackId);

    void addTrackArtist(String trackId, String artistId);

    void addTrackGenre(String trackId, String genreId);

    List<TrackDto> searchTracksByName(String query, int page, int size);

}
