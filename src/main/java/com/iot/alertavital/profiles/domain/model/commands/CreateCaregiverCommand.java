package com.iot.alertavital.profiles.domain.model.commands;

import com.iot.alertavital.iam.domain.model.aggregates.User;

public record CreateCaregiverCommand(User user, String phoneNumber) {
}
