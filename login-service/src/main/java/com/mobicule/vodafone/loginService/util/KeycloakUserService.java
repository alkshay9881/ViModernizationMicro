package com.mobicule.vodafone.loginService.util;


import com.mobicule.vodafone.loginService.auth.controller.OtpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class KeycloakUserService {


    private static final Logger log =
            LoggerFactory.getLogger(KeycloakUserService.class);

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    // =========================
    // 1. GET ADMIN TOKEN
    // =========================
    private String getAdminToken() {

        String tokenUrl = serverUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body =
                "grant_type=password" +
                        "&client_id=" + adminClientId +
                        "&username=" + adminUsername +
                        "&password=" + adminPassword;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(tokenUrl, entity, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // =========================
    // 2. FIND USER
    // =========================
    private String findUserId(String username, String token) {

        String url = serverUrl + "/admin/realms/" + realm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        List users = response.getBody();

        if (users != null && !users.isEmpty()) {
            Map user = (Map) users.get(0);
            return (String) user.get("id");
        }

        return null;
    }

    // =========================
    // 3. CREATE USER
    // =========================
    private String createUser(String username, String token) {

        String url = serverUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("enabled", true);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(user, headers);

        ResponseEntity<Void> response =
                restTemplate.postForEntity(url, entity, Void.class);

        // Extract userId from Location header
        String location = response.getHeaders().getLocation().toString();
        return location.substring(location.lastIndexOf("/") + 1);
    }

    // =========================
    // 4. SET/UPDATE PASSWORD
    // =========================
    private void setPassword(String userId, String password, String token) {

        String url = serverUrl + "/admin/realms/" + realm +
                "/users/" + userId + "/reset-password";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> pass = new HashMap<>();
        pass.put("type", "password");
        pass.put("value", password);
        pass.put("temporary", false);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(pass, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    // =========================
    // MAIN METHOD
    // =========================
    public void createOrUpdateUser(String username, String password) {

        String token = getAdminToken();

        String userId = findUserId(username, token);

        if (userId == null) {
            log.info("User not found → creating...");
            userId = createUser(username, token);
        } else {
            log.info("User exists → updating password...");
        }

        setPassword(userId, password, token);

        log.info("Done.");
    }
}

