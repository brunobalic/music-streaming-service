package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.UserPlayHistory;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserPlayHistoryImpl implements UserPlayHistory {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUrL_musicMs;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public UserPlayHistoryImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
        this.token = token;
        this.rest = rest;
    }

    @Override
    public List<TrackDto> getUserPlayHistory(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.getAccessToken());

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);

        ResponseEntity<TrackDto[]> response = rest.getRestTemplate()
                .exchange(baseUrL_musicMs + "/play-history/{userId}",
                        HttpMethod.GET,
                        requestEntity,
                        TrackDto[].class,
                        params);

        if (response.getBody() == null) return new ArrayList<>();

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
