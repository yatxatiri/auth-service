package com.cibertec.auth_service.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentType {
    DNI("1"), 
    RUC("6");

    private final String code;

    DocumentType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    @JsonValue
    public String toJson() {
        return getCode().toLowerCase();
    }
}
