package com.toeic.auth.infrastructure.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toeic.auth.application.dto.AuthResponseDto;
import com.toeic.auth.application.service.IAuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    
    private final RestTemplate restTemplate;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin.token-url}")
    private String tokenUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    /**
     * Authenticate user and get a Keycloak access token.
     */
    public AuthResponseDto authenticate(String username, String password) {
        String url = "http://localhost:8080/realms/TrungHieu/protocol/openid-connect/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("scope", "openid profile email");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

                AuthResponseDto responseDto = AuthResponseDto.builder()
                                                .access_token(jsonNode.get("access_token").asText())
                                                .refresh_token(jsonNode.get("refresh_token").asText())
                                                .build();

                return responseDto; 
            }
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }

        return null; 
    }

    /**
     * Refresh an expired token using the refresh token.
     */
    public AuthResponseDto refreshToken(String refreshToken) {
        String url = tokenUrl + "/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

                AuthResponseDto responseDto = AuthResponseDto.builder()
                                                .access_token(jsonNode.get("access_token").asText())
                                                .refresh_token(jsonNode.get("refresh_token").asText())
                                                .build();

                return responseDto; 
            }
        } catch (Exception e) {
            System.out.println("Token refresh failed: " + e.getMessage());
        }

        return null; 
    }

    /**
     * Validate a token by checking it against Keycloak.
     */
    public boolean validateToken(String token) {
        String url = String.format("http://localhost:8080/realms/%s/protocol/openid-connect/userinfo", realm);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return response.getStatusCode().is2xxSuccessful(); 
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }

        return false; 
    }

    /**
     * Logout user by revoking the refresh token.
     */
    public boolean logout(String refreshToken) {
        String url = tokenUrl + "/logout";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response.getStatusCode().is2xxSuccessful(); 
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }

        return false; 
    }
}
