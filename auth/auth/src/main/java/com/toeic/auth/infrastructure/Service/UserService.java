package com.toeic.auth.infrastructure.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toeic.auth.application.dto.AddUserDto;
import com.toeic.auth.application.dto.UpdateUserDto;
import com.toeic.auth.application.mapper.UserMapper;
import com.toeic.auth.application.service.IUserService;
import com.toeic.auth.domain.exception.NotFoundException;
import com.toeic.auth.domain.model.Users;
import com.toeic.auth.domain.repository.IUsersRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class UserService implements IUserService {

    private final IUsersRepository userRepository;
    private final KeycloakAdminService keyCloakAdminService;
    private final SyncService syncService;

    @Override
    @Transactional
    public Users registerUser(AddUserDto userDto) {

        String adminToken = keyCloakAdminService.getKeycloakAdminToken();

        Users user = UserMapper.mapToTestSessionDto(userDto);

        String keycloakId;
        try {
            keycloakId = keyCloakAdminService.createUserInKeycloak(user, adminToken);

            user.setKeycloakId(keycloakId);

            keyCloakAdminService.updateKeycloakUser(keycloakId, user.getEmail(), 
            user.getFirstname(), user.getLastname(), user.getRole(), adminToken);

            return userRepository.save(user);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;   
    }

    @Override
    @Transactional
    public Users updateUser(UpdateUserDto userDto) {
        // Find the user in the local database
        Users findStudent = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new NotFoundException("Cannot find user with username: " + userDto.getUsername()));

        // Get Keycloak admin token
        String adminToken = keyCloakAdminService.getKeycloakAdminToken();

        // Get the Keycloak user ID
        String keycloakUserId = keyCloakAdminService.getKeycloakUserId(userDto.getUsername(), adminToken);
        if (keycloakUserId == null) {
            throw new NotFoundException("User not found in Keycloak: " + userDto.getUsername());
        }

        // Update user fields in the local database
        findStudent.setEmail(userDto.getEmail());
        findStudent.setRole(userDto.getRole());
        findStudent.setPassword(userDto.getPassword());

        // Save updated user in the database
        Users updatedUser = userRepository.save(findStudent);

        // Update user in Keycloak (email & role)
        keyCloakAdminService.updateKeycloakUser(keycloakUserId, userDto.getEmail(), 
        userDto.getFirstname(), userDto.getLastname(), userDto.getRole(), adminToken);

        keyCloakAdminService.updateUserPassword(keycloakUserId, userDto.getPassword(), adminToken);

        return updatedUser;
    }

    @Override
    @Transactional
    public Optional<Users> findByUsername(String username) {
        Optional<Users> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user;
        }

        // Get Keycloak admin token
        String adminToken = keyCloakAdminService.getKeycloakAdminToken();
        // Find user ID in Keycloak
        String keycloakUserId = keyCloakAdminService.getKeycloakUserId(username, adminToken);

        if (keycloakUserId != null) {
            System.out.println("User exists in Keycloak but not in database. Syncing: " + username);

            // Fetch full user details from Keycloak
            Users keycloakUser = syncService.syncUserFromKeycloak(keycloakUserId, adminToken);

            if (keycloakUser != null) {
                return Optional.of(keycloakUser); 
            }
        }

        return Optional.empty(); 
    }

    @Override
    @Transactional
    public List<Users> findAll() {
        syncService.syncAllUsersFromKeycloak();
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(int userId) {
        // Find the user in the database
        Users findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + userId));
    
        // Get Keycloak admin token
        String adminToken = keyCloakAdminService.getKeycloakAdminToken();
    
        // Get Keycloak User ID
        String keycloakUserId = findUser.getKeycloakId();
        if (keycloakUserId != null) {
            boolean deletedFromKeycloak = keyCloakAdminService.deleteUserFromKeycloak(keycloakUserId, adminToken);
    
            if (!deletedFromKeycloak) {
                throw new RuntimeException("Failed to delete user from Keycloak, aborting deletion.");
            }
        }
    
        // Delete user from local database
        userRepository.delete(findUser);
        System.out.println("User deleted successfully from database and Keycloak: " + findUser.getUsername());
    }
    
}
