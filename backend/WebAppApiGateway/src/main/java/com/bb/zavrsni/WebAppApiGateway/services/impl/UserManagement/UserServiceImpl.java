package com.bb.zavrsni.WebAppApiGateway.services.impl.UserManagement;

import com.bb.zavrsni.WebAppApiGateway.auth.JwtForMyMicroservices;
import com.bb.zavrsni.WebAppApiGateway.exceptions.UniversalException;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.RatingDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.User.UserDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.UserMS.UserService;
import com.bb.zavrsni.WebAppApiGateway.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Value("${my_values.microservices.user-ms.base-url}")
    private String baseUrl_userMs;

    private final JwtForMyMicroservices token;
    private final RestTemplateInstance rest;

    @Autowired
    public UserServiceImpl(JwtForMyMicroservices token, RestTemplateInstance rest) {
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
    public UserDto createNewUser(UserCreationDto userCreationDto) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<UserCreationDto> requestEntity = new HttpEntity<>(userCreationDto, headers);

        ResponseEntity<UserDto> response = rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/users",
                        HttpMethod.POST,
                        requestEntity,
                        UserDto.class);

        return response.getBody();
    }

    @Override
    public UserDto getUserById(String id) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        ResponseEntity<UserDto> response = rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/users/{id}",
                        HttpMethod.GET,
                        requestEntity,
                        UserDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public UserDto getUserByAuth0Id(String auth0Id) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("auth0id", auth0Id);
        //params.put("id", "non existent id");

        ResponseEntity<UserDto> response = rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/users/auth0?id={auth0id}",
                        HttpMethod.GET,
                        requestEntity,
                        UserDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public UserDto getUserByUsername(String username) {
        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        ResponseEntity<UserDto> response = rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/users/un/{username}",
                        HttpMethod.GET,
                        requestEntity,
                        UserDto.class,
                        params);

        return response.getBody();
    }

    @Override
    public void addToFavourites(String what, String userId, String id) {
        if (Arrays.stream(FavouriteWhat.values()).noneMatch(favouriteWhat -> favouriteWhat.getValue().equals(what))) {
            throw new UniversalException("Error - addToFavourites() - Wrong content type!");
        }

        HttpHeaders headers = createHeaders(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("user-id", userId);
        body.add(what + "-id", id);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        Map<String, String> params = new HashMap<>();
        params.put("what", what);

        rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/fav/{what}s",
                        HttpMethod.POST,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void removeFromFavourites(String what, String userId, String id) {
        if (Arrays.stream(FavouriteWhat.values()).noneMatch(favouriteWhat -> favouriteWhat.getValue().equals(what))) {
            throw new UniversalException("Error - removeFromFavourites() - Wrong content type!");
        }

        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        // * mogao bi koristiti i UriComponentsBuilder

        Map<String, String> params = new HashMap<>();
        params.put("what", what);
        params.put("user-id", userId);
        params.put("content-id", id);

        rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/fav/{what}s?user-id={user-id}&{what}-id={content-id}", // {what}s -> moram dodat ovaj 's' jer je mnozina, to bi trablo drugacije hendlat...
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void addRatingFor(String what, String userId, String id, RatingDto ratingDto) {
        if (Arrays.stream(RateWhat.values()).noneMatch(rateWhat -> rateWhat.getValue().equals(what))) {
            throw new UniversalException("Error - addRatingFor() - Wrong content type!");
        }

        HttpHeaders headers = createHeaders(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("user-id", userId);
        body.add(what + "-id", id);
        body.add("rating-dto", ratingDto);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        Map<String, String> params = new HashMap<>();
        params.put("what", what);

        rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/ratings/{what}s",
                        HttpMethod.POST,
                        requestEntity,
                        Void.class,
                        params);
    }

    @Override
    public void removeRatingFrom(String what, String userId, String id) {
        if (Arrays.stream(RateWhat.values()).noneMatch(rateWhat -> rateWhat.getValue().equals(what))) {
            throw new UniversalException("Error - removeRatingFor() - Wrong content type!");
        }

        HttpHeaders headers = createHeaders(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("what", what);
        params.put("user-id", userId);
        params.put("content-id", id);

        rest.getRestTemplate()
                .exchange(baseUrl_userMs + "/ratings/{what}s?user-id={user-id}&{what}-id={content-id}",
                        HttpMethod.DELETE,
                        requestEntity,
                        Void.class,
                        params);
    }

}
