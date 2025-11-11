package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;

import java.util.Optional;

public interface CaregiverCommandService {
    Optional<Caregiver> handle(CreateCaregiverCommand command);
}
