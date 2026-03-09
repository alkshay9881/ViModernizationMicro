package com.mobicule.vodafone.loginService.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mobicule.vodafone.loginService.common.dto.TokenResponse;
import com.mobicule.vodafone.loginService.common.exceptions.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Slf4j
public class KeycloakTokenUtil {

    @Value("${keycloak.token.url}")
    private String tokenUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

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

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String token = (String) response.getBody().get("access_token");
            DecodedJWT jwt = JWT.decode(token);
            log.info("Pre-login token expires at: {}", jwt.getExpiresAt());
            return token;
        }

        throw new UnauthorizedAccessException("Failed to get pre-login token");
    }

    // =====================================================
    // 2) USER LOGIN TOKEN (Username + Password)
    // =====================================================
    public TokenResponse requestToken(String username, String password) {
        return fetchToken("password", username, password, null);
    }

    // =====================================================
    // 3) REFRESH TOKEN
    // =====================================================
    public TokenResponse refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new UnauthorizedAccessException("Refresh token is required");
        }
        return fetchToken("refresh_token", null, null, refreshToken);
    }

    // =====================================================
    // COMMON METHOD TO FETCH TOKEN FROM KEYCLOAK
    // =====================================================
    private TokenResponse fetchToken(String grantType, String username, String password, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        if ("password".equals(grantType)) {
            body.add("username", username);
            body.add("password", password);
        } else if ("refresh_token".equals(grantType)) {
            body.add("refresh_token", refreshToken);
        }

        try {

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map res = response.getBody();
                TokenResponse tokenResponse = new TokenResponse();
                tokenResponse.setAccess_token((String) res.get("access_token"));
                tokenResponse.setRefresh_token((String) res.get("refresh_token"));
                tokenResponse.setToken_type((String) res.get("token_type"));
                tokenResponse.setExpires_in((Integer) res.get("expires_in"));
                tokenResponse.setRefresh_expires_in((Integer) res.get("refresh_expires_in"));

                DecodedJWT jwt = JWT.decode(tokenResponse.getAccess_token());
                log.info("Token expires at: {}", jwt.getExpiresAt());

                return tokenResponse;
            }

            // throw new UnauthorizedAccessException("Failed to fetch token from Keycloak");
        }catch (ResourceAccessException e) {
            log.info("Timeout Exception : ", e);

            throw new ResourceAccessException("Request timed out while communicating with the server. Please retry.");

        } catch (HttpClientErrorException e) {

            log.info("HTTP Client Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString(), e);
            if (e.getStatusCode() == org.springframework.http.HttpStatus.BAD_REQUEST) {
                throw new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST,
                        "Client error occurred: " + e.getResponseBodyAsString());
            } else {
                throw new HttpClientErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server error occurred: " + e.getResponseBodyAsString());
            }

        }
        catch (HttpServerErrorException e) {

            log.info("HTTP Server Error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString(), e);
            throw new HttpServerErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error occurred: " + e.getResponseBodyAsString());
        }
        return null;
    }

}