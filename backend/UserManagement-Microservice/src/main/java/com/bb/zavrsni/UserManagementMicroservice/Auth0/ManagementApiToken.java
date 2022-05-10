package com.bb.zavrsni.UserManagementMicroservice.Auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
@NoArgsConstructor
public class ManagementApiToken {

    @Value("${spring.security.client.registration.auth0.client-id}")
    private String client_id;

    @Value("${spring.security.client.registration.auth0.client-secret}")
    private String client_secret;

    @Value("${spring.security.client.provider.auth0.issuer-uri}")
    private String issuer_uri;

    @Value("${my_values.auth0.audience-management-api}")
    private String api_identifier;

    @Autowired
    RestTemplate restTemplate; // za sada necu koristiti ovo vec Unirest

    private JsonNode tokenJson;
    private String accessToken;

    private void requestNewToken() {
        HttpResponse<JsonNode> response = Unirest
                .post(issuer_uri + "oauth/token")
                .header("content-type", "application/x-www-form-urlencoded")
                .body("grant_type=client_credentials" + "&" +
                        "client_id=" + client_id + "&" +
                        "client_secret=" + client_secret + "&" +
                        "audience=" + api_identifier)
                .asJson();
        tokenJson = response.getBody();
        accessToken = tokenJson.getObject().getString("access_token");

        System.out.println("Auth0 Management API token - dohvacen");
        System.out.println(accessToken);
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        if (expiresAt.after(new Date())) { // expiresAt kasnije od sada
            return false;
        }
        return true;
    }

    private boolean isTokenValid() {
        if (accessToken == null || accessToken.equals("")) {
            return false;
        } else if (isTokenExpired(JWT.decode(accessToken))) {
            return false;
        }
        return true;
    }

    public String getAccessToken() {
        if (!isTokenValid()) {
            requestNewToken();
        }
        return accessToken;
    }

}
