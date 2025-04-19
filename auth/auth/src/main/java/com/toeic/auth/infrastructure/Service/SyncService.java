package com.toeic.auth.infrastructure.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toeic.auth.application.service.ISyncService;
import com.toeic.auth.domain.enums.Role;
import com.toeic.auth.domain.model.Users;
import com.toeic.auth.domain.repository.IUsersRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SyncService implements ISyncService {

    private final KeycloakAdminService keycloakAdminService;
    private final IUsersRepository usersRepository;
    private final RestTemplate restTemplate;

    public SyncService(KeycloakAdminService keycloakAdminService, IUsersRepository usersRepository, 
    RestTemplate restTemplate) {
        this.keycloakAdminService = keycloakAdminService;
        this.usersRepository = usersRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public void syncAllUsersFromKeycloak() {
        String token = keycloakAdminService.getKeycloakAdminToken();
        String userJson = keycloakAdminService.getUsersFromKeycloak(token);

        List<Users> keycloakUsers = parseKeycloakUsersJson(userJson, token);

        for (Users keycloakUser : keycloakUsers) {
            Optional<Users> userOpt = usersRepository.findByKeycloakId(keycloakUser.getKeycloakId());
            if (userOpt.isPresent()) {
                Users user = userOpt.get();
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstname(keycloakUser.getFirstname());
                user.setLastname(keycloakUser.getLastname());
                usersRepository.save(user);
            } else {
                usersRepository.save(keycloakUser);
            }
        }
    }

    @Override
    @Transactional
    public Users syncUserFromKeycloak(String keycloakUserId, String adminToken) {
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/users/%s", keycloakUserId);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Failed to fetch user from Keycloak: " + response.getBody());
                return null;
            }
    
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userNode = objectMapper.readTree(response.getBody());
    
            // Extract user details with proper null checks
            String username = userNode.hasNonNull("username") ? userNode.path("username").asText() : null;
            String email = userNode.hasNonNull("email") ? userNode.path("email").asText() : null;
            String firstName = userNode.hasNonNull("firstName") ? userNode.path("firstName").asText() : null;
            String lastName = userNode.hasNonNull("lastName") ? userNode.path("lastName").asText() : null;
    
            if (username == null) {
                System.out.println("User from Keycloak has no username, skipping sync.");
                return null;
            }
    
            // Check if user already exists in database
            Optional<Users> existingUser = usersRepository.findByKeycloakId(keycloakUserId);
            if (existingUser.isPresent()) {
                System.out.println("User already exists in database: " + username);
                return existingUser.get();
            }
    
            // Get role from Keycloak
            String roleName = getClientRole(keycloakUserId, adminToken);
            Role userRole = Role.fromString(roleName);
    
            // Create and save user in database
            Users newUser = new Users(username, email, firstName, lastName, userRole, keycloakUserId);
            return usersRepository.save(newUser);
    
        } catch (Exception e) {
            System.out.println("Error syncing user from Keycloak: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void syncAllUsersToKeycloak() {
        String adminToken = keycloakAdminService.getKeycloakAdminToken();
        List<Users> localUsers = usersRepository.findAll();
    
        for (Users user : localUsers) {
            // Check if user exists in Keycloak
            String keycloakUserId = keycloakAdminService.getKeycloakUserId(user.getUsername(), adminToken);
    
            if (keycloakUserId == null) {
                // User does not exist → Create in Keycloak
                try {
                    keycloakAdminService.createUserInKeycloak(user, adminToken);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // User exists → Update details in Keycloak
                keycloakAdminService.updateKeycloakUser(keycloakUserId, user.getEmail(), 
                user.getFirstname(), user.getLastname(), user.getRole(), adminToken);
            }
        }
        System.out.println("Sync from local database to Keycloak completed.");
    }
    

    private List<Users> parseKeycloakUsersJson(String userJson, String token) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userJson);
            List<Users> users = new ArrayList<>();
            
            for (JsonNode userNode : usersNode) {
                String keycloakId = userNode.get("id").asText();
                String username = userNode.get("username").asText();
                String email = userNode.get("email").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                String parseRole = getClientRole(keycloakId, token);

                Role clientRole = Role.fromString(parseRole);
    
                users.add(new Users(username, email, firstName, lastName, clientRole, keycloakId));
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Keycloak users JSON", e);
        }
    }

      // Helper method to get the client role (client_admin) for the user
      private String getClientRole(String keycloakId, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://localhost:8080/admin/realms/TrungHieu/users/%s/role-mappings", keycloakId);

        try {
            // Set up HTTP headers with Bearer Token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Send GET request to Keycloak API
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Check for Unauthorized (401) or Forbidden (403)
            if (responseEntity.getStatusCode().is4xxClientError()) {
                System.out.println("Error: Unauthorized (401) or Forbidden (403). Please check your token.");
                return "Unauthorized";
            }

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());

            // Correct path: clientMappings → java-demo
            JsonNode javaDemoClient = rootNode.path("clientMappings").path("java-demo");

            // Ensure "java-demo" exists and is an object
            if (!javaDemoClient.isMissingNode() && javaDemoClient.isObject()) {
                JsonNode mappings = javaDemoClient.path("mappings");

                if (mappings.isArray()) {
                    for (JsonNode mapping : mappings) {
                        String roleName = mapping.path("name").asText();
                        if (roleName.contains("client_")) {
                            System.out.println("Role Found: " + roleName);
                            return roleName;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }

        System.out.println("No matching role found.");
        return "No Role"; 
    }
}
