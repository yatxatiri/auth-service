package com.cibertec.auth_service.dtos;

import com.cibertec.auth_service.entities.DocumentType;
import com.cibertec.auth_service.entities.Role;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role type;
    private DocumentType documentType;
    private String number;
    private String address;
    private String telephone;
}
