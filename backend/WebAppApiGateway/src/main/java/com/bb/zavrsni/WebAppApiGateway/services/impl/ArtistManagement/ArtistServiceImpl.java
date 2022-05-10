package com.bb.zavrsni.WebAppApiGateway.services.impl.ArtistManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Value("${my_values.microservices.artist-ms.base-url}")
    private String baseUri;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public ArtistServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
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
    public ArtistDto createNewArtist(ArtistCreationDto artistCreationDto) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<ArtistCreationDto> requestEntity = new HttpEntity<>(artistCreationDto, headers);

        ResponseEntity<ArtistDto> response = rest.getRestTemplate()
                .exchange(baseUri + "/artists",
                        HttpMethod.POST,
                        requestEntity,
                        ArtistDto.class);

        return response.getBody();
    }

    @Override
    public ArtistDto getArtist(String artistId) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", artistId);

        ResponseEntity<ArtistDto> response = rest.getRestTemplate()
                .exchange(baseUri + "/artists/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        ArtistDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public ArtistDto getArtistByAuth0Id(String artistAuth0Id) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", artistAuth0Id);

        ResponseEntity<ArtistDto> response = rest.getRestTemplate()
                .exchange(baseUri + "/artists/auth0?id={id}",
                        HttpMethod.GET,
                        requestEntity,
                        ArtistDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public List<ArtistDto> searchArtistsByName(String query, int page, int size) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("p", String.valueOf(page));
        params.put("s", String.valueOf(size));

        ResponseEntity<ArtistDto[]> response = rest.getRestTemplate()
                .exchange(baseUri + "/artists/search?q={q}&p={p}&s={s}",
                        HttpMethod.GET,
                        requestEntity,
                        ArtistDto[].class,
                        params);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
