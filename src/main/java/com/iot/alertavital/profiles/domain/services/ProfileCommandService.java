package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
}
