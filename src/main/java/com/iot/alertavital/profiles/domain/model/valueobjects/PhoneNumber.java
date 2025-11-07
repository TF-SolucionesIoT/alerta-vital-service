package com.iot.alertavital.profiles.domain.model.valueobjects;

/**
 * Value Object para representar un número de teléfono
 */
public record PhoneNumber(String number) {
    public PhoneNumber {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }
        if (number.length() < 10 || number.length() > 15) {
            throw new IllegalArgumentException("El número de teléfono debe tener entre 10 y 15 dígitos");
        }
    }
}
