package com.iot.alertavital.profiles.domain.model.commands;

import java.time.LocalDate;

public record UpdatePatientCommand(
        Long patientId,
        LocalDate birthday
) {
}
