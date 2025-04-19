package com.toeic.auth.infrastructure.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SyncService syncService;

    // Sync from Keycloak to Local DB every hour
    @Scheduled(fixedRate = 3600000) // 1 hour = 3600000 ms
    @Transactional
    public void syncUsersFromKeycloak() {
        System.out.println("Starting Keycloak → Local DB sync...");
        syncService.syncAllUsersFromKeycloak();
        System.out.println("Completed Keycloak → Local DB sync.");
    }

    // Sync from Local DB to Keycloak every day at 3 AM
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void syncUsersToKeycloak() {
        System.out.println("Starting Local DB → Keycloak sync...");
        syncService.syncAllUsersToKeycloak();
        System.out.println("Completed Local DB → Keycloak sync.");
    }
}
