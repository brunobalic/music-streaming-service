package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.TrackService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUri_musicMs;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public TrackServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
        this.token = token;
        this.rest = rest;
    }

    private HttpHeaders createHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setBearerAuth(token.getAccessToken());
        return headers;
    }

    @Override
    public TrackDto uploadNewTrack(TrackCreationDto trackCreationDto, ByteArrayResource audioFile, String artistId) {
        HttpHeaders headers = createHeaders(MediaType.MULTIPART_FORM_DATA);

        // file extension that is original filename
        String fileExtension = Objects.requireNonNull(audioFile.getFilename()).substring(audioFile.getFilename().lastIndexOf('.') + 1);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("json", trackCreationDto);
        body.add("file", audioFile);
        body.add("file-extension", fileExtension);
        body.add("artistId", artistId);
        body.add("uploaderJwt", "jwt - nisam ga poslao"); // todo

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TrackDto> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks",
                        HttpMethod.POST,
                        requestEntity,
                        TrackDto.class);

        return response.getBody();
    }

    @Override
    public TrackDto getTrack(String trackId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", trackId);

        ResponseEntity<TrackDto> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        TrackDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public void deleteTrack(String trackId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", trackId);

        rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks/{id}",
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void addTrackArtist(String trackId, String artistId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("tr-id", trackId);
        params.put("art-id", artistId);

        rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks/{tr-id}/artist/{art-id}",
                        HttpMethod.POST,
                        requestEntity,
                        TrackDto.class,
                        params);
    }

    @Override
    public void addTrackGenre(String trackId, String genreId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("tr-id", trackId);
        params.put("gnr-id", genreId);

        rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks/{tr-id}/genre/{gnr-id}",
                        HttpMethod.POST,
                        requestEntity,
                        TrackDto.class,
                        params);
    }

    @Override
    public List<TrackDto> searchTracksByName(String query, int page, int size) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("p", String.valueOf(page));
        params.put("s", String.valueOf(size));

        ResponseEntity<TrackDto[]> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/tracks/search?q={q}&p={p}&s={s}",
                        HttpMethod.GET,
                        requestEntity,
                        TrackDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
