package com.iot.alertavital.profiles.domain.services;


import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;

import java.util.Optional;

public interface PatientCommandService{
    Optional<Patient> handle(CreatePatientCommand command);
}
