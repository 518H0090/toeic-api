package com.toeic.user.domain.enums;

public enum ContentType {
    TEXT,
    IMAGE,
    AUDIO;

    public static ContentType fromString(String contentType) {
        for (ContentType content_type : ContentType.values()) {
            if (content_type.name().equalsIgnoreCase(contentType)) {
                return content_type;
            }
        }
        throw new IllegalArgumentException("No enum constant for content type: " + contentType);
    }
}
