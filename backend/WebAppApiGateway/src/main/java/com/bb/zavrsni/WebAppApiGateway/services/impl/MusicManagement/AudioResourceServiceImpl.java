package com.bb.zavrsni.WebAppApiGateway.services.impl.MusicManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.exceptions.UniversalException;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AudioResourceService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.TrackService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class AudioResourceServiceImpl implements AudioResourceService {

    @Value("${my_values.microservices.music-ms.base-url}")
    private String baseUrl_musicMS;

    @Value("${my_values.microservices.streaming-ms.base-url}")
    private String baseUrl_streamingMS;

    private final TrackService trackService;
    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public AudioResourceServiceImpl(TrackService trackService,
                                    JwtForMyMicroservices token,
                                    RestTemplateInstance restTemplateInstance) {
        this.trackService = trackService;
        this.token = token;
        this.rest = restTemplateInstance;
    }

    @Override
    public byte[] getAudioResource(String trackId, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("app-userId", userId);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("userId", userId);

        HttpEntity<?> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = rest.getRestTemplate()
                .exchange(baseUrl_musicMS + "/media/audio/{trackId}",
                        HttpMethod.GET,
                        requestEntity,
                        byte[].class,
                        trackId);

        return response.getBody();
    }

    @Override
    public byte[] getAudioResourceArtistRequest(String trackId, String artistId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        TrackDto track = trackService.getTrack(trackId);
        String filename;
        if (track.getFilename() == null || track.getFilename().equals("")) filename = "universalTrack";
        else filename = track.getFilename();

        ResponseEntity<byte[]> response = rest.getRestTemplate()
                .exchange(baseUrl_streamingMS + "/media/audio/{filename}",
                        HttpMethod.GET,
                        requestEntity,
                        byte[].class,
                        filename);

        return response.getBody();
    }
}
