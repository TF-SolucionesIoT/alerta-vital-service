package com.iot.alertavital.profiles.domain.model.commands;

import com.iot.alertavital.iam.domain.model.aggregates.User;

import java.time.LocalDate;

public record CreatePatientCommand(User user, LocalDate date) {
}
