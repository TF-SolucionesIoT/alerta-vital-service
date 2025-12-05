package com.iot.alertavital.monitoring.interfaces.REST.transform;

import com.iot.alertavital.monitoring.domain.model.commands.CreateReadingDeviceCommand;
import com.iot.alertavital.monitoring.interfaces.REST.resources.CreateReadingDeviceRequest;

public class CreateReadingDeviceCommandFromResourceAssembler {

    public static CreateReadingDeviceCommand toCommand(CreateReadingDeviceRequest request) {
        return new CreateReadingDeviceCommand(
                request.spO2(),
                request.bpm(),
                request.deviceId(),
                request.bpDiastolic(),
                request.bpSystolic()
        );
    }
}