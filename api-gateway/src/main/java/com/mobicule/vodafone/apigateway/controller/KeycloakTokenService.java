package com.mobicule.vodafone.apigateway.controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import java.util.Map;

public class KeycloakTokenService {

    private final WebClient webClient;

    public KeycloakTokenService() {
        this.webClient = WebClient.create("http://localhost:8080/realms/master/protocol/openid-connect/token");
    }

    public String getAccessToken(String username, String password) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", "micro-auth");
        formData.add("client_secret", "Wq9EPVluxOLF0fOqSMb7NL3zEUZFvzXc");
        formData.add("username", username);
        formData.add("password", password);

        Mono<Map> response = webClient.post()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, Object> tokenResponse = response.block();
        return (String) tokenResponse.get("access_token");
    }

    public static void main(String[] args) {
        KeycloakTokenService service = new KeycloakTokenService();
        String token = service.getAccessToken("admin", "admin");
        System.out.println("Access Token: " + token);
    }
}

