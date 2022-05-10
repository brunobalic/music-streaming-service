package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.GenreDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.GenreService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUri;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public GenreServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
        this.token = token;
        this.rest = rest;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.getAccessToken());

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<GenreDto[]> response = rest.getRestTemplate()
                .exchange(baseUri + "/genres",
                        HttpMethod.GET,
                        requestEntity,
                        GenreDto[].class);

        Objects.requireNonNull(response.getBody());

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
