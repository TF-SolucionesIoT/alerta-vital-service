package com.iot.alertavital.profiles.domain.model.commands;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;

import java.time.LocalDate;

public record CreateProfileCommand(
    User user,
    UserType userType,
    String phoneNumber,    // Para caregivers
    LocalDate birthday     // Para patients
) {
    public CreateProfileCommand {
        if (user == null) {
            throw new IllegalArgumentException("User no puede ser nulo");
        }
        if (userType == null) {
            throw new IllegalArgumentException("UserType no puede ser nulo");
        }

        // Validar que se proporcione la información correcta según el tipo
        if (userType == UserType.CAREGIVER && (phoneNumber == null || phoneNumber.trim().isEmpty())) {
            throw new IllegalArgumentException("PhoneNumber es requerido para caregivers");
        }
        if (userType == UserType.PATIENT && birthday == null) {
            throw new IllegalArgumentException("Birthday es requerido para patients");
        }
    }
}
