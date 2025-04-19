package com.toeic.auth.application.service;

import com.toeic.auth.domain.model.Users;

public interface ISyncService {
    void syncAllUsersFromKeycloak();
    void syncAllUsersToKeycloak();
    Users syncUserFromKeycloak(String keycloakUserId, String adminToken);
}
