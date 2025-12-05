package com.iot.alertavital.monitoring.domain.services;

import com.iot.alertavital.monitoring.domain.model.commands.CreateReadingDeviceCommand;
import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;

import java.util.Optional;

public interface ReadingDeviceCommandService {
    Optional<ReadingDevice> handle(CreateReadingDeviceCommand command);
}
