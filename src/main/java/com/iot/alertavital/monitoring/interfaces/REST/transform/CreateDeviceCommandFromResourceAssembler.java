package com.iot.alertavital.monitoring.interfaces.REST.transform;

import com.iot.alertavital.monitoring.domain.model.commands.CreateDeviceCommand;
import com.iot.alertavital.monitoring.interfaces.REST.resources.CreateDeviceRequest;

public class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand FromResource(CreateDeviceRequest request){
        return new CreateDeviceCommand(request.deviceId());
    }
}
