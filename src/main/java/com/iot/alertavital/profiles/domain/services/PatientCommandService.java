package com.iot.alertavital.profiles.domain.services;


import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdatePatientCommand;

import java.util.Optional;

public interface PatientCommandService{
    Optional<Patient> handle(CreatePatientCommand command);
    Optional<Patient> handle(UpdatePatientCommand command);
}
