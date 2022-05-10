package com.bb.zavrsni.UserManagementMicroservice.Auth0;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagementApi {

    @Autowired
    private ManagementApiToken token;

    @Value("${spring.security.client.provider.auth0.issuer-uri}")
    private String issuer_uri;

    @Value("${my_values.auth0.audience-management-api}")
    private String api_identifier;

    @Value("${my_values.auth0.connection}")
    private String auth0_connection; // auth0 db connection

    @Getter
    @Setter
    @AllArgsConstructor
    private static class BodyObj {
        String email;
        String password;
        String username;
        String connection;
    }

    public Map<String, String> createNewUser(String email, String password, String username) {
        BodyObj bodyObj = new BodyObj(email, password, username, auth0_connection);
        HttpResponse<JsonNode> response = Unirest.post(api_identifier + "users")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .body(bodyObj)
                .asJson();

        Map<String, String> map = new HashMap<>();
        if (response.isSuccess()) {
            map.put("user_id", response.getBody().getObject().getString("user_id"));
        } else {
            map.put("error_message", response.getBody().getObject().getString("message"));
        }
        return map;
    }

    private String getRoleId(String roleName) {
        HttpResponse<JsonNode> response = Unirest.get(api_identifier + "roles?name_filter={role_name}")
                .routeParam("role_name", roleName)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .asJson();

        if (response.isSuccess()) {
            String roleId = response.getBody().getArray().getJSONObject(0).getString("id");
            System.out.println(roleId);
            return roleId;
        }
        else {
            System.out.println("Error in gettint role ID");
            return null;
        }
    }

    public int assignRolesToUser(String userId, String roleName) {
        String roleId = getRoleId(roleName);
        List<String> rolesIds = new ArrayList<>();
        rolesIds.add(roleId);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.putArray("roles").add(roleId);

        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post(api_identifier + "users/{userId}/roles")
                    .routeParam("userId", userId)
                    .header("Authorization", "Bearer " + token.getAccessToken())
                    .contentType("application/json")
                    .body(mapper.writeValueAsString(node))
                    .asJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (response == null) {
            System.out.println("response je null - auth0 management api assignRolesToUser()");
            return 0;
        }

        System.out.println(response.getStatus() + " - assign roles http status");

        if (response.getStatus() / 100 == 4) {
            String message_2 = response.getBody().getObject().getString("message"); //
            System.out.println(message_2);
        }
        return response.getStatus();
    }

}
