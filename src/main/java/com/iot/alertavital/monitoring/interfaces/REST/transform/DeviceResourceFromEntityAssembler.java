package com.iot.alertavital.monitoring.interfaces.REST.transform;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.interfaces.REST.resources.CreateDeviceResponse;

public class DeviceResourceFromEntityAssembler {
    public static CreateDeviceResponse fromEntity(Device device) {
        return new CreateDeviceResponse(
                device.getId(),
                device.getDeviceId(),
                device.getStatus(),
                device.getPatient().getUser().fullName()
        );
    }
}
