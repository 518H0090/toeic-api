package com.toeic.auth.adapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.toeic.auth.application.service.ISyncService;
@RestController
@RequestMapping("/sync")
public class SyncController {

    private final ISyncService syncService;

    public SyncController(ISyncService syncService) {
        this.syncService = syncService;
    }

    // Endpoint to sync all users from Keycloak to local database
    @PostMapping("/all-users-from-keycloak") 
    public ResponseEntity<String> syncAllUsersFromKeycloak() {
        syncService.syncAllUsersFromKeycloak();
        return ResponseEntity.ok("All users synced from Keycloak successfully!");
    }

    // Endpoint to sync all users from local database to Keycloak
    @PostMapping("/all-users-to-keycloak") // not
    public ResponseEntity<String> syncAllUsersToKeycloak() {
        syncService.syncAllUsersToKeycloak();
        return ResponseEntity.ok("All users synced to Keycloak successfully!");
    }
}
