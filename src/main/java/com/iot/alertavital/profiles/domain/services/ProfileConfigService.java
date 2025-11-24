package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.commands.ChangePasswordCommand;

import java.util.Optional;

public interface ProfileConfigService {
    Optional<User> handle(ChangePasswordCommand command);
}
