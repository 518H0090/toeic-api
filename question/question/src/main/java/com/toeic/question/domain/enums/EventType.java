package com.toeic.question.domain.enums;

public enum EventType {
    CREATE,
    UPDATE,
    DELETE;

    public static EventType fromString(String eventType) {
        for (EventType event_type : EventType.values()) {
            if (event_type.name().equalsIgnoreCase(eventType)) {
                return event_type;
            }
        }
        throw new IllegalArgumentException("No enum constant for event type: " + eventType);
    }
}
