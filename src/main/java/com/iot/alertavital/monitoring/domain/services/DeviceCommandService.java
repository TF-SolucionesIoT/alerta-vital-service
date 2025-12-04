package com.iot.alertavital.monitoring.domain.services;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.commands.CreateAlertCommand;
import com.iot.alertavital.monitoring.domain.model.commands.CreateDeviceCommand;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(CreateDeviceCommand command);
    Optional<?> handle(CreateAlertCommand command);
}
