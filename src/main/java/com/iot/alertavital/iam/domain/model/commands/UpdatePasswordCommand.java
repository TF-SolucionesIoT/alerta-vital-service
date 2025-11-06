package com.iot.alertavital.iam.domain.model.commands;

public record UpdatePasswordCommand(
        Long userId,
        String currentPassword,
        String newPassword
) {
}
