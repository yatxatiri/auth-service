package com.cibertec.auth_service.dtos;

import com.cibertec.auth_service.entities.DocumentType;
import com.cibertec.auth_service.entities.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role type;
    private DocumentType documentType;
    private String number;
    private String address;
    private String telephone;
    private boolean active;
}
