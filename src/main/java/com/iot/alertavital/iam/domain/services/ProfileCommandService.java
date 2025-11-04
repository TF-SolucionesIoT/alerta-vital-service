package com.iot.alertavital.iam.domain.services;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.model.commands.UpdateInformationCommand;
import com.iot.alertavital.iam.domain.model.commands.UpdatePasswordCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<User> handle(UpdateInformationCommand command);
    Optional<User> handle(UpdatePasswordCommand command);
}
