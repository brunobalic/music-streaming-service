package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.CollectionCreationDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUrL_musicMs;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public AlbumServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
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
    public AlbumDto createNewAlbum(CollectionCreationDto collectionCreationDto,
                                   ByteArrayResource imageFile,
                                   String artistId) {

        HttpHeaders headers = createHeaders(MediaType.MULTIPART_FORM_DATA);

        System.out.println(token.getAccessToken());

        String fileExtension = Objects.requireNonNull(imageFile.getFilename()).substring(imageFile.getFilename().lastIndexOf('.') + 1);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("data", collectionCreationDto);
        body.add("file", imageFile.getByteArray());
        body.add("file-extension", fileExtension);
        body.add("key-artistId", artistId);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AlbumDto> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums",
                        HttpMethod.POST,
                        requestEntity,
                        AlbumDto.class);

        return response.getBody();
    }

    @Override
    public AlbumDto getAlbumById(String albumId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", albumId);

        ResponseEntity<AlbumDto> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        AlbumDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public List<AlbumDto> getAllPublicAlbumsFromArtist(String artistId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", artistId);

        ResponseEntity<AlbumDto[]> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums/artist/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        AlbumDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getAllAlbumsFromArtist(String artistId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", artistId);

        ResponseEntity<AlbumDto[]> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums/artist/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        AlbumDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    @Override
    public void deleteAlbumById(String albumId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", albumId);

        rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums/{id}",
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public List<AlbumDto> searchAlbumsByName(String query, int page, int size) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("p", String.valueOf(page));
        params.put("s", String.valueOf(size));

        ResponseEntity<AlbumDto[]> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/albums/search?q={q}&p={p}&s={s}",
                        HttpMethod.GET,
                        requestEntity,
                        AlbumDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
