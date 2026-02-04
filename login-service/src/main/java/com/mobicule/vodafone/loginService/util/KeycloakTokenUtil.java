package com.mobicule.vodafone.loginService.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mobicule.vodafone.loginService.dto.TokenResponse;
import com.mobicule.vodafone.loginService.platformCommons.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Component
public class KeycloakTokenUtil {

    @Value("${keycloak.token.url}")
    private String tokenUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger log =
            LoggerFactory.getLogger(KeycloakTokenUtil.class);

    // =====================================================
    // 1) PRE-LOGIN TOKEN (Client Credentials)
    // =====================================================
    public String getPreLoginToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            String token = (String) response.getBody().get("access_token");

            DecodedJWT jwt = JWT.decode(token);
            Date expiry = jwt.getExpiresAt();

            log.info("Pre-login token expires at: " + expiry);

            return token;
        }

        throw new UnauthorizedAccessException("Failed to get pre-login token");
    }

    // =====================================================
    // 2) USER LOGIN TOKEN (Username + Password)
    // =====================================================
    public String getUserLoginToken(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            String token = (String) response.getBody().get("access_token");

            DecodedJWT jwt = JWT.decode(token);
            Date expiry = jwt.getExpiresAt();

          log.info("User token expires at: " + expiry);

            return token;
        }

        throw new UnauthorizedAccessException("Invalid username/password");
    }



    public TokenResponse requestToken(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);


        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            Map res = response.getBody();

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccess_token((String) res.get("access_token"));
            tokenResponse.setRefresh_token((String) res.get("refresh_token"));
            tokenResponse.setToken_type((String) res.get("token_type"));
            tokenResponse.setExpires_in((Integer) res.get("expires_in"));
            tokenResponse.setRefresh_expires_in((Integer) res.get("refresh_expires_in"));

            // Decode expiry log (optional)
            DecodedJWT jwt = JWT.decode(tokenResponse.getAccess_token());
            Date expiry = jwt.getExpiresAt();
            log.info("Token expires at: {}", expiry);

            return tokenResponse;
        }

        throw new UnauthorizedAccessException("Failed to fetch token from Keycloak");
    }

}
