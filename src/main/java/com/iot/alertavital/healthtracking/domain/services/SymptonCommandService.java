package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateSymptonCommand;
import com.iot.alertavital.healthtracking.domain.model.commands.DeleteSymptonCommand;

import java.util.Optional;

public interface SymptonCommandService {
    Optional<Sympton> handle(CreateSymptonCommand command);
    void handle(DeleteSymptonCommand command);

}

