package com.toeic.auth.domain.enums;

public enum Role {
    client_admin,
    client_user;

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No enum constant for role: " + role);
    }
}
