package com.mobicule.vodafone.loginService.auth.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class KeycloakUserService {


    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    // =========================
    // 1. GET ADMIN TOKEN
    // =========================
//


    private String getAdminToken() {
        try {
            String tokenUrl = serverUrl + "/realms/vodafone/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Use client_credentials, no username/password
            String body =
                    "grant_type=client_credentials" +
                            "&client_id=" + adminClientId +
                            "&client_secret=" + clientSecret;

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, entity, Map.class);


            log.info("token22  -- "+(String) response.getBody().get("access_token"));

            return (String) response.getBody().get("access_token");

        } catch (ResourceAccessException e) {
            log.info("Timeout Exception : ", e);
            throw new ResourceAccessException("Request timed out while communicating with the server. Please retry.");

        } catch (HttpClientErrorException e) {
            log.info("HTTP Client Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString(), e);
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            log.info("HTTP Server Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString(), e);
            throw new HttpServerErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }



    // =========================
    // 2. FIND USER
    private String findUserId(String username, String token) {
        try {
            String url = serverUrl + "/admin/realms/" + realm + "/users?username=" +
                    URLEncoder.encode(username, StandardCharsets.UTF_8);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity,
                            new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            List<Map<String, Object>> users = response.getBody();

            if (users != null && !users.isEmpty()) {
                return (String) users.get(0).get("id");
            }
        } catch (Exception e) {
            log.error("Error finding user ID for username: {}", username, e);
        }

        return null;
    }
    // =========================
    // 3. CREATE USER
    // =========================
    private String createUser(String username, String token) {
        try {
            String url = serverUrl + "/admin/realms/" + realm + "/users";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> user = new HashMap<>();
            user.put("username", username);
            user.put("enabled", true);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity(url, entity, Void.class);

            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new RuntimeException("Failed to create user, status: " + response.getStatusCode());
            }

            URI locationUri = response.getHeaders().getLocation();
            if (locationUri == null) {
                throw new RuntimeException("User created but Location header missing");
            }

            return locationUri.getPath().substring(locationUri.getPath().lastIndexOf("/") + 1);

        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + username, e);
        }
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

        try {
            ResponseEntity<Void> response =
                    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

            // 🔎 Keycloak returns 204 NO_CONTENT if successful
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                log.info("Password set successfully for userId: " + userId);
            } else {
                throw new RuntimeException(" Failed to set password. Status: "
                        + response.getStatusCode());
            }

        } catch (HttpClientErrorException e) {
            log.info(" Client error while setting password: "
                    + e.getResponseBodyAsString());
            throw new RuntimeException("Password reset failed", e);

        } catch (HttpServerErrorException e) {
            log.info("Server error while setting password: "
                    + e.getResponseBodyAsString());
            throw new RuntimeException("Password reset failed", e);

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while setting password", e);
        }
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


    // =========================
// 5. LOGOUT USER (INVALIDATE TOKENS)
// =========================
    private void logoutUser(String userId, String token) {

        String url = serverUrl + "/admin/realms/" + realm +
                "/users/" + userId + "/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                log.info("All sessions invalidated for userId: {}", userId);
            } else {
                log.warn("Unexpected response while logout: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error while logging out userId: {}", userId, e);
            throw new RuntimeException("Failed to logout user sessions", e);
        }
    }


    // =========================
// 6. INVALIDATE BY USERNAME
// =========================
    public void invalidateUserSessions(String username) {

        String token = getAdminToken();

        String userId = findUserId(username, token);

        if (userId == null) {
            log.warn("User not found for username: {}", username);
            return;
        }

        logoutUser(userId, token);
    }



}

