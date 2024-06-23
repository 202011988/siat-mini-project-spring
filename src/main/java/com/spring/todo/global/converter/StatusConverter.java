package com.spring.todo.global.converter;

import com.spring.todo.domain.task.entity.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {
	
	@Override
    public String convertToDatabaseColumn(Status attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }
	 
	@Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return Status.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown database value: " + dbData);
        }
    }
}
