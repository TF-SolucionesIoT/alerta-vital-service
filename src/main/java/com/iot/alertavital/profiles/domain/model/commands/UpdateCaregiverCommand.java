package com.iot.alertavital.profiles.domain.model.commands;

public record UpdateCaregiverCommand(
        Long caregiverId,
        String phoneNumber
) {
}
