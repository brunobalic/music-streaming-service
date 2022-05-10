package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.PlaylistService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUri;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public PlaylistServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
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
    public PlaylistDto createNewPlaylist(PlaylistCreationDto playlistCreationDto) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<PlaylistCreationDto> requestEntity = new HttpEntity<>(playlistCreationDto, headers);

        ResponseEntity<PlaylistDto> response = rest.getRestTemplate()
                .exchange(baseUri + "/playlists",
                        HttpMethod.POST,
                        requestEntity,
                        PlaylistDto.class);

        return response.getBody();
    }

    @Override
    public PlaylistDto getPlaylist(String playlistId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", playlistId);

        ResponseEntity<PlaylistDto> response = rest.getRestTemplate()
                .exchange(baseUri + "/playlists/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        PlaylistDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public List<PlaylistDto> getAllUserPlaylists(String userId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);

        ResponseEntity<PlaylistDto[]> response = rest.getRestTemplate()
                .exchange(baseUri + "/playlists/user/{userId}",
                        HttpMethod.GET,
                        requestEntity,
                        PlaylistDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    @Override
    public void deletePlaylist(String playlistId, String userId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", playlistId);
        params.put("userId", userId);

        rest.getRestTemplate()
                .exchange(baseUri + "/playlists/{id}/{userId}",
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void addTrack(String playlistId, String trackId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("pl-id", playlistId);
        params.put("tr-id", trackId);

        rest.getRestTemplate()
                .exchange(baseUri + "/playlists/{pl-id}/track/{tr-id}",
                        HttpMethod.POST,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void removeTrack(String playlistId, String trackId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("pl-id", playlistId);
        params.put("tr-id", trackId);

        rest.getRestTemplate()
                .exchange(baseUri + "/playlists/{pl-id}/track/{tr-id}",
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public List<PlaylistDto> searchPlaylistsByName(String query, int page, int size) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("p", String.valueOf(page));
        params.put("s", String.valueOf(size));

        ResponseEntity<PlaylistDto[]> response = rest.getRestTemplate()
                .exchange(baseUri + "/playlists/search?q={q}&p={p}&s={s}",
                        HttpMethod.GET,
                        requestEntity,
                        PlaylistDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
