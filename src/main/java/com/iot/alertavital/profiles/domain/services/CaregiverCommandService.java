package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateCaregiverCommand;

import java.util.Optional;

public interface CaregiverCommandService {
    Optional<Caregiver> handle(CreateCaregiverCommand command);
    Optional<Caregiver> handle(UpdateCaregiverCommand command);
}
