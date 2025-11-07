package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateDisturbanceCommand;
import com.iot.alertavital.healthtracking.domain.model.commands.DeleteDisturbanceCommand;

import java.util.Optional;

public interface DisturbanceCommandService {
    Optional<Disturbance> handle(CreateDisturbanceCommand command);
    void handle(DeleteDisturbanceCommand command);

}
