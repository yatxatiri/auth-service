package com.cibertec.auth_service.converter;

import com.cibertec.auth_service.entities.DocumentType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String>{

    @Override
    public String convertToDatabaseColumn(DocumentType attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public DocumentType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (DocumentType type : DocumentType.values()){
            if (type.getCode().equals(dbData)) return type;
        }
        throw new IllegalArgumentException("Unknow type: " + dbData);
    }

}
