package com.cibertec.auth_service.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    SELLER, ADMIN, WAREHOUSE;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
