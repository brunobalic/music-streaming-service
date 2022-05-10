package com.bb.zavrsni.MusicManagementMicroservice.services.impl;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.AlbumDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.dto.CollectionCreationDto;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.*;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.AlbumGenreRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.AlbumRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.ArtistAlbumRepository;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.TrackAlbumRepository;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.AlbumService;
import com.bb.zavrsni.MusicManagementMicroservice.utils.RestTemplateInstance;
import com.bb.zavrsni.MusicManagementMicroservice.utils.mappers.AlbumMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Value("${my_values.microservices.streaming-ms.base-url}")
    private String baseUrl_streamingMs;

    private final AlbumRepository albumRepository;
    private final AlbumGenreRepository albumGenreRepository;
    private final ArtistAlbumRepository artistAlbumRepository;
    private final TrackAlbumRepository trackAlbumRepository;
    private final RestTemplateInstance rest;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository,
                            AlbumGenreRepository albumGenreRepository,
                            ArtistAlbumRepository artistAlbumRepository,
                            TrackAlbumRepository trackAlbumRepository,
                            RestTemplateInstance restTemplateInstance,
                            AlbumMapper albumMapper) {

        this.albumRepository = albumRepository;
        this.albumGenreRepository = albumGenreRepository;
        this.artistAlbumRepository = artistAlbumRepository;
        this.trackAlbumRepository = trackAlbumRepository;
        this.rest = restTemplateInstance;
        this.albumMapper = albumMapper;
    }

    private void validateCollectionCreationData(CollectionCreationDto data) {
        // ovo mi je ne svida
        // za sada neka stoji ali validacije se drugacije rade... (preko javax @Valid anotacija...)
        boolean flag = true;

        if (data.getArtistId() == null) flag = false;

        if (data.getName().length() > 64) flag = false;

        if (data.getDescription().length() > 255) flag = false;

        List<String> allTypes = new ArrayList<>();
        allTypes.add("album");
        allTypes.add("single");
        allTypes.add("ep");
        allTypes.add("lp");
        allTypes.add("live");
        if (!allTypes.contains(data.getCollectionType().toLowerCase())) {
            flag = false;
        }

        if (!flag) {
            throw new UniversalException("Collection data not valid!");
        }
    }

    @Override
    public AlbumDto createAlbum(ByteArrayResource collectionCreationDtoJson,
                                String artistId,
                                ByteArrayResource artworkFile,
                                String fileExtension) {

        // * Converting json file to CollectionCreationDto object
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = new String(collectionCreationDtoJson.getByteArray());
        CollectionCreationDto collCreationDto;
        try {
            collCreationDto = objectMapper.readValue(jsonString, CollectionCreationDto.class);
        } catch (JsonProcessingException e) {
            throw new UniversalException("Error de-serialising json file!");
        }

        // * Setting artist id
        collCreationDto.setArtistId(Integer.valueOf(artistId));

        // * Validating data
        validateCollectionCreationData(collCreationDto);

        // * Save album to db
        Album album = albumRepository.save(albumMapper.map(collCreationDto));

        // * Send file to streaming ms
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // todo - dodati token za streaming ms
        //headers.setBearerAuth();

        String artworkType = "album"; // special value to differentiate artwork types (album, playlist, artist)

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("key-file", artworkFile.getByteArray());
        body.add("key-file-extension", fileExtension);
        body.add("key-type", artworkType);
        body.add("key-id", album.getId().toString());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rest.getRestTemplate().postForEntity(baseUrl_streamingMs + "/api/media/image/upload", requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            String msg = Objects.requireNonNull(response.getBody()).toString();
            //ApiErrorResponse x = (ApiErrorResponse) response.getBody();
            throw new UniversalException("Error uploading artwork image to streaming service!", msg);
        }

        String filename = response.getBody();
        album.setFilename(filename);
        albumRepository.save(album);

        // * All steps done - return newly created AlbumDto object
        return albumMapper.albumToAlbumDto(album);
    }

    @Override
    public AlbumDto getAlbumById(int id) {
        Album album = albumRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        //.orElseThrow(() -> new UniversalException("", "")); // ili ovako ako zelim baciti svoj exception
        System.out.println(album.getId());
        return albumMapper.albumToAlbumDto(album);
    }

    @Override
    public void deleteAlbum(int albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow(ResourceNotFoundException::new);
        albumRepository.delete(album);
    }

    // todo - dodati jos metode za npr. promjenu imena albuma...

    @Override
    public AlbumDto addTracks(int albumId, List<Track> tracks) {
        tracks.forEach(track -> {
            trackAlbumRepository.save(new TrackAlbum(track.getId(), albumId));
        });

        // todo
        // ...
        // update number_of_tracks

        Album album = albumRepository.findById(albumId).orElseThrow(ResourceNotFoundException::new);
        return albumMapper.albumToAlbumDto(album);
    }

    @Override
    public void addArtists(int albumId, List<Artist> artists) {
        artists.forEach(artist -> {
            artistAlbumRepository.save(new ArtistAlbum(artist.getId(), albumId));
        });

    }

    @Override
    public void addGenres(int albumId, List<Genre> genres) {
        genres.forEach(genre -> {
            albumGenreRepository.save(new AlbumGenre(albumId, genre.getId()));
        });
    }

    @Override
    public void removeGenres(int albumId, List<Genre> genres) {
        genres.forEach(genre -> {
            albumGenreRepository.findByAlbumAndGenre(albumId, genre.getId());
        });
    }

    @Override
    public List<AlbumDto> getAllAlbumsFromArtist(int artistId) {
        List<Album> albums = albumRepository.findAllByArtistId(artistId);
        List<AlbumDto> result = new ArrayList<>();
        // todo - odradit preko mappera - dodat list<> u mapper
        albums.forEach(album -> result.add(albumMapper.albumToAlbumDto(album)));
        return result;
    }

    // todo - Optional<Integer> page // moze li ovako ??
    @Override
    public List<AlbumDto> searchAlbums(String searchString, int page, int size) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 5;
        Pageable pageable = PageRequest.of(page, size); // 5 fixed - elements per page

        List<Album> albums = albumRepository.searchAlbums(searchString.toLowerCase(), pageable);
        List<AlbumDto> result = new ArrayList<>();
        albums.forEach(album -> result.add(albumMapper.albumToAlbumDto(album)));
        return result;
    }

}
