package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateTreatmentCommand;

import java.util.Optional;

public interface TreatmentCommandService {
    Optional<Treatments> handle(CreateTreatmentCommand command);
}

