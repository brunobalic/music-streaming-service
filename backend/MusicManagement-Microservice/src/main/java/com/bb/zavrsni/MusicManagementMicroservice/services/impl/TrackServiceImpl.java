package com.bb.zavrsni.MusicManagementMicroservice.services.impl;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.ArtistTrack;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Track;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.TrackGenre;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.ArtistTrackRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.TrackGenreRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.TrackRepository;
import com.bb.zavrsni.MusicManagementMicroservice.models.dto.TrackCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.TrackService;
import com.bb.zavrsni.MusicManagementMicroservice.utils.AudioFileValidator;
import com.bb.zavrsni.MusicManagementMicroservice.utils.FileMetadata;
import com.bb.zavrsni.MusicManagementMicroservice.utils.RestTemplateInstance;
import com.bb.zavrsni.MusicManagementMicroservice.utils.mappers.TrackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Objects;

@Service
public class TrackServiceImpl implements TrackService {

    @Value("${my_values.microservices.streaming-ms.base-url}")
    private String baseUrl_streamingMs;

    private final TrackRepository trackRepository;
    private final ArtistTrackRepository artistTrackRepository;
    private final TrackGenreRepository trackGenreRepository;
    private final AudioFileValidator audioFileValidator;
    private final RestTemplateInstance rest;
    private final TrackMapper trackMapper;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository,
                            ArtistTrackRepository artistTrackRepository,
                            TrackGenreRepository trackGenreRepository,
                            AudioFileValidator audioFileValidator,
                            RestTemplateInstance rest,
                            TrackMapper trackMapper) {

        this.trackRepository = trackRepository;
        this.artistTrackRepository = artistTrackRepository;
        this.trackGenreRepository = trackGenreRepository;
        this.audioFileValidator = audioFileValidator;
        this.rest = rest;
        this.trackMapper = trackMapper;
    }

    private void validateTrackCreationDtoData(TrackCreationDto obj) {
        // todo napraviti ovu validaciju drugacije
        // @ Valid...

        // ovdje bi moglo baciti null pointer exc.
        if (obj.getTitle().length() > 64) throw new UniversalException("Track title too long, must be less than 64 characters");
        if (obj.getDescription().length() > 255) throw new UniversalException("Track description too long, must be less than 255 characters");
    }

    @Override
    public TrackDto createTrack(TrackCreationDto trackCreationDto,
                                ByteArrayResource file,
                                String fileExtension,
                                String uploaderId) {

        // ! Ovo za sada ne koristim pa cu samo setirati kako mi odgovara...
        trackCreationDto.setDescription("");
        trackCreationDto.setPublicTrack(true);

        // * validate trackCreationDto data
        validateTrackCreationDtoData(trackCreationDto);

        // * construct Track object that will be persisted to db
        Track toCreate = trackMapper.trackCreationDtoToTrack(trackCreationDto);

        // * set artist id to uploader id
        toCreate.setArtist_id(Integer.parseInt(uploaderId));

        // * validate audio file
        FileMetadata fileMetadata = audioFileValidator.validateAudioFile(file, fileExtension);

        // * set needed file metadata
        toCreate.setDuration_sec(fileMetadata.getDuration().intValue());

        // * at this point track data and file are valid

        // * persist Track object to db
        Track createdTrack = trackRepository.save(toCreate);

        // * send audio file to streaming ms for storage
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // todo - dodati token za streaming ms
        //headers.setBearerAuth();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getByteArray());
        body.add("file-extension", fileMetadata.getFormatName());
        body.add("id", createdTrack.getId());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rest.getRestTemplate()
                .exchange(baseUrl_streamingMs + "/api/media/audio/upload",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new UniversalException("Error uploading audio file to streaming ms!", Objects.requireNonNull(response.getBody()).toString());
        }

        String filename = response.getBody();
        createdTrack.setFilename(filename);
        trackRepository.save(createdTrack);

        // * all steps done

        // * return newly created Track object
        return trackMapper.trackToTrackDto(createdTrack);
    }

    @Override
    public TrackDto getTrackById(int id) {
        Track track = trackRepository
                .findById(id)
                .orElseThrow(() -> new UniversalException(String.format("Track with id: %s does not exist!", id)));
        return trackMapper.trackToTrackDto(track);
    }

    @Override
    public void deleteTrack(int trackId) {
        Track toDelete = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new UniversalException(String.format("Delete not possible - track with id: %s does not exist!", trackId)));
        trackRepository.delete(toDelete);
    }

    @Override
    public void addArtist(int trackId, int artistId) {
        artistTrackRepository.save(new ArtistTrack(artistId, trackId));
    }

    @Override
    public void addGenre(int trackId, int genreId) {
        trackGenreRepository.save(new TrackGenre(trackId, genreId));
    }

    @Override
    public List<TrackDto> searchTracks(String searchString, int page, int size) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Track> tracks = trackRepository.searchTracks(searchString.toLowerCase(), pageable);
        return trackMapper.trackListToTrackDtoList(tracks);
    }

    @Override
    public void incrementPlayCount(int trackId) {
        Track track = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new UniversalException(String.format("Cannot increment play count - track with id: %s does not exist!", trackId)));
        track.setPlay_count(track.getPlay_count() + 1);
        trackRepository.save(track);
    }
}
