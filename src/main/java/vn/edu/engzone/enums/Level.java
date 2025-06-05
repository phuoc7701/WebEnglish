package vn.edu.engzone.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Level {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    @JsonCreator
    public static Level forValue(String value) {
        for (Level level : Level.values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid level: " + value);
    }
}
