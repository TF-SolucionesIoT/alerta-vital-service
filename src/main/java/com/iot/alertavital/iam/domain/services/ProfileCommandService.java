package com.iot.alertavital.iam.domain.services;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.commands.UpdateInformationCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<User> handle(UpdateInformationCommand command);

}
