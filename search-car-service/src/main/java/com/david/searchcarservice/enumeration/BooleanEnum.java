package com.david.searchcarservice.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BooleanEnum {
    FALSE("false"),
    TRUE("true");

    private final String value;

    BooleanEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BooleanEnum fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
