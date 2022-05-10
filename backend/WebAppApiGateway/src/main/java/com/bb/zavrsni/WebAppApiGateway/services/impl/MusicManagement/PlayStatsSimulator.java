package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayStatsSimulator {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUri_musicMs;

    private final RestTemplateInstance rest;

    @Autowired
    public PlayStatsSimulator(RestTemplateInstance rest) {
        this.rest = rest;
    }

    public Object[] getArtistPlayStats(String artistId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBearerAuth(); // todo

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("a", artistId);

        ResponseEntity<Object[]> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/stats/{a}",
                        HttpMethod.GET,
                        requestEntity,
                        Object[].class,
                        params);

        return response.getBody();
    }

    public Object[] getTop50FromArtist(String artistId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBearerAuth(); // todo

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("a", artistId);

        ResponseEntity<Object[]> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/stats/{a}/top50",
                        HttpMethod.GET,
                        requestEntity,
                        Object[].class,
                        params);

        return response.getBody();
    }

    public Object[] getGloablTop100() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBearerAuth(); // todo

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> response = rest.getRestTemplate()
                .exchange(baseUri_musicMs + "/stats/global/top100",
                        HttpMethod.GET,
                        requestEntity,
                        Object[].class);

        return response.getBody();
    }

}
