package com.iot.alertavital.iam.domain.model.commands;

import java.time.LocalDate;

public record UpdateInformationCommand(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String username,
        String phoneNumber,  // Para caregivers
        LocalDate birthday   // Para patients
) {
}
