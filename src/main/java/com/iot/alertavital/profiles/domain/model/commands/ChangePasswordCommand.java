package com.iot.alertavital.profiles.domain.model.commands;

public record ChangePasswordCommand(String password, String newPassword) {
}
