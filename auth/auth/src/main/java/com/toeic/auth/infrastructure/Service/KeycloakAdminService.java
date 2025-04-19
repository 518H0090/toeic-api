package com.toeic.auth.infrastructure.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.toeic.auth.domain.enums.Role;
import com.toeic.auth.domain.model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakAdminService {
    private final RestTemplate restTemplate;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin.token-url}")
    private String tokenUrl;

    public KeycloakAdminService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to get the Keycloak admin token
    public String getKeycloakAdminToken() {
        String url = tokenUrl + "/token";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("username", "htthieu");
        map.add("password", "Chaymetwa123@");
        map.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        String responseBody = response.getBody();
        String accessToken = null;

        if (responseBody != null) {
            try {
                JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
                accessToken = jsonNode.get("access_token").asText();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse access token from Keycloak response", e);
            }
        }

        return accessToken;
    }

    // Method to get all users from Keycloak
    public String getUsersFromKeycloak(String token) {
        String keycloakUserApiUrl = "http://localhost:8080/admin/realms/TrungHieu/users";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(keycloakUserApiUrl, HttpMethod.GET, entity,
                String.class);

        return response.getBody();
    }

    public String getKeycloakUserId(String username, String adminToken) {
        // Keycloak API URL to search for a user by username
        String url = "http://localhost:8080/admin/realms/TrungHieu/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                // Ensure we have at least one user in the response
                if (rootNode.isArray() && rootNode.size() > 0) {
                    return rootNode.get(0).path("id").asText();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("User not found in Keycloak: " + username);
        return null; // Return null if user is not found
    }

    public void updateKeycloakUser(String userId, String email, String firstName, String lastName, Role role, String adminToken) {
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/users/%s", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Update user email and enable account
        String requestBody = "{ \"email\": \"" + email + "\", " +
                     "\"firstName\": \"" + firstName + "\", " +
                     "\"lastName\": \"" + lastName + "\", " +
                     "\"enabled\": true }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("User email updated in Keycloak: " + email);
        } else {
            System.out.println("Failed to update user email in Keycloak: " + response.getBody());
        }

        // Assign new role in Keycloak
        updateUserRoleInKeycloak(userId, role, adminToken);
    }

    public void updateUserPassword(String userId, String hashedPassword, String adminToken) {
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/users/%s/reset-password", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send pre-hashed password
        String requestBody = "{ \"type\": \"password\", \"value\": \"" + hashedPassword + "\", \"temporary\": false }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Password updated in Keycloak for user: " + userId);
        } else {
            System.out.println("Failed to update password in Keycloak: " + response.getBody());
        }
    }

    public void updateUserRoleInKeycloak(String userId, Role newRole, String adminToken) {
        String clientUuid = getClientUuid(clientId, adminToken);
        if (clientUuid == null) {
            System.out.println("Failed to retrieve client UUID for " + clientId);
            return;
        }
    
        // Step 1: Get all current roles (id + name)
        List<Role> currentRoles = getUserClientRoles(userId, clientUuid, adminToken);
    
        // Step 2: Remove existing roles (if any)
        if (!currentRoles.isEmpty()) {
            removeUserClientRoles(userId, clientUuid, currentRoles, adminToken);
        }
    
        // Step 3: Get the new role's ID
        String newRoleId = getClientRoleId(clientUuid, newRole.name(), adminToken);
        if (newRoleId == null) {
            System.out.println("Role " + newRole.name() + " does not exist in Keycloak for client " + clientId);
            return;
        }
    
        // Step 4: Assign the new role
        String url = String.format(
                "http://localhost:8080/admin/realms/TrungHieu/users/%s/role-mappings/clients/%s",
                userId, clientUuid);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        // JSON payload (id + name required by Keycloak)
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode roleArray = objectMapper.createArrayNode();
        
        ObjectNode roleObject = objectMapper.createObjectNode();
        roleObject.put("id", newRoleId);
        roleObject.put("name", newRole.name());
        roleArray.add(roleObject);
        
        String requestBody = roleArray.toString();
    
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Successfully assigned new role in Keycloak: " + newRole);
        } else {
            System.out.println("Failed to assign Keycloak client role: " + response.getBody());
        }
    }

    private List<Role> getUserClientRoles(String userId, String clientUuid, String adminToken) {
        String url = String.format(
                "http://localhost:8080/admin/realms/TrungHieu/users/%s/role-mappings/clients/%s",
                userId, clientUuid);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
    
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    
        List<Role> roles = new ArrayList<>();
    
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rolesArray = objectMapper.readTree(response.getBody());
    
                for (JsonNode roleNode : rolesArray) {
                    String roleName = roleNode.get("name").asText();
                    try {
                        roles.add(Role.fromString(roleName)); // Convert to Role enum
                    } catch (IllegalArgumentException e) {
                        System.out.println("Warning: Unknown role found in Keycloak - " + roleName);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    
        return roles;
    }

    private void removeUserClientRoles(String userId, String clientUuid, List<Role> roles, String adminToken) {
        String url = String.format(
                "http://localhost:8080/admin/realms/TrungHieu/users/%s/role-mappings/clients/%s",
                userId, clientUuid);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        // Convert roles (Enum) into JSON payload (fetching ID from Keycloak)
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode roleArray = objectMapper.createArrayNode();
        
        for (Role role : roles) {
            String roleId = getClientRoleId(clientUuid, role.name(), adminToken); // Fetch ID from Keycloak
            if (roleId == null) {
                System.out.println("Role ID not found for: " + role.name());
                continue; // Skip if role ID is not found
            }
    
            ObjectNode roleObject = objectMapper.createObjectNode();
            roleObject.put("id", roleId); // Add role ID
            roleObject.put("name", role.name()); // Add role name
            roleArray.add(roleObject);
        }
    
        // If no roles found, return early
        if (roleArray.isEmpty()) {
            System.out.println("No valid roles found to remove.");
            return;
        }
    
        String requestBody = roleArray.toString(); // Convert to JSON string
    
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Removed previous roles from Keycloak.");
        } else {
            System.out.println("Failed to remove previous roles: " + response.getBody());
        }
    }

    // Step to Fetch Client UUID
    private String getClientUuid(String clientId, String adminToken) {
        String url = "http://localhost:8080/admin/realms/TrungHieu/clients?clientId=" + clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode clientsArray = objectMapper.readTree(response.getBody());

                if (clientsArray.isArray() && clientsArray.size() > 0) {
                    return clientsArray.get(0).get("id").asText();
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Step to Fetch Client Role ID
    private String getClientRoleId(String clientUuid, String roleName, String adminToken) {
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/clients/%s/roles", clientUuid);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rolesArray = objectMapper.readTree(response.getBody());

                for (JsonNode role : rolesArray) {
                    if (role.get("name").asText().equals(roleName)) {
                        return role.get("id").asText(); // Return the Role ID
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null; // Role not found
    }

    public boolean deleteUserFromKeycloak(String keycloakUserId, String adminToken) {
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/users/%s", keycloakUserId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("User deleted from Keycloak: " + keycloakUserId);
                return true;
            } else {
                System.out.println("Failed to delete user from Keycloak: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error deleting user from Keycloak: " + e.getMessage());
            return false;
        }
    }

    public String createUserInKeycloak(Users user, String adminToken) throws JsonProcessingException {
        String url = "http://localhost:8080/admin/realms/TrungHieu/users";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request body using a Map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", user.getUsername());
        requestBody.put("email", user.getEmail());
        requestBody.put("firstName", user.getFirstname());
        requestBody.put("lastName", user.getLastname()); 
        requestBody.put("enabled", true);

        // Set password credentials
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", user.getPassword() != null ? user.getPassword() : user.getUsername());
        credentials.put("temporary", false);

        requestBody.put("credentials", List.of(credentials));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestBody);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Created user in Keycloak: " + user.getUsername());

            String keycloakId = getKeycloakUserId(user.getUsername(), adminToken);

            updateKeycloakUser(keycloakId, user.getEmail(), 
            user.getFirstname(), user.getLastname(), user.getRole(), adminToken);

            return keycloakId;
        } else {
            System.out.println("Failed to create user in Keycloak: " + response.getBody());
        }
        return null;
    }

}
