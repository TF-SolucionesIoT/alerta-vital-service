package com.iot.alertavital.profiles.domain.model.valueobjects;

import java.time.LocalDate;

/**
 * Value Object para representar una fecha de nacimiento
 */
public record Birthday(LocalDate birthday) {
    public Birthday {
        if (birthday == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro");
        }
    }
}
