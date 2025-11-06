package com.iot.alertavital.iam.domain.model.commands;

public record UpdateInformationCommand(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String username
) {
}
