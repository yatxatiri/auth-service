package com.cibertec.auth_service.dtos;

import com.cibertec.auth_service.entities.DocumentType;
import com.cibertec.auth_service.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String name;

    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Role type;
    private DocumentType documentType;
    private String number;
    private String address;
    private String telephone;
}
