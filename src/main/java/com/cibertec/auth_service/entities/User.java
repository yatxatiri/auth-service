package com.cibertec.auth_service.entities;

import java.time.LocalDateTime;

import com.cibertec.auth_service.converter.DocumentTypeConverter;
import com.cibertec.auth_service.converter.RoleConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true)
    private String email;
    
    private String password;     

    @Convert(converter = RoleConverter.class)
    @Column(length = 50, nullable = false)
    @Builder.Default
    private Role type = Role.SELLER;

    @Convert(converter = DocumentTypeConverter.class)
    @Column(name = "identity_document_type_id", length = 1)
    private DocumentType documentType; 

    @Column(length = 20)
    private String number;

    private String address;

    @Column(length = 50)
    private String telephone;
    
    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean locked = false;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

}
