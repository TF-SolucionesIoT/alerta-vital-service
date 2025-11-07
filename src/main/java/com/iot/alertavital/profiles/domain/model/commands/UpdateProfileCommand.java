package com.iot.alertavital.profiles.domain.model.commands;

import java.time.LocalDate;

public record UpdateProfileCommand(
    Long profileId,
    String phoneNumber,    // Para caregivers
    LocalDate birthday     // Para patients
) {
    public UpdateProfileCommand {
        if (profileId == null) {
            throw new IllegalArgumentException("ProfileId no puede ser nulo");
        }
    }
}
