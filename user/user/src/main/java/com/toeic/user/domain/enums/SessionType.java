package com.toeic.user.domain.enums;

public enum SessionType {
    COMPLETED,
    PROGRESS,
    FAILED;

    public static SessionType fromString(String sessionType) {
        for (SessionType session_type : SessionType.values()) {
            if (session_type.name().equalsIgnoreCase(sessionType)) {
                return session_type;
            }
        }
        throw new IllegalArgumentException("No enum constant for content type: " + sessionType);
    }
}
