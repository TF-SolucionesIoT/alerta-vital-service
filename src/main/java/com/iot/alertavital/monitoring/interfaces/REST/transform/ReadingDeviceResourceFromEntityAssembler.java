package com.iot.alertavital.monitoring.interfaces.REST.transform;

import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.monitoring.interfaces.REST.resources.GetAllReadingByDescResponse;

public class ReadingDeviceResourceFromEntityAssembler {
    public static GetAllReadingByDescResponse toResource(ReadingDevice readingDevice) {
        return new GetAllReadingByDescResponse(
                readingDevice.getId(),
                readingDevice.getSpO2(),
                readingDevice.getPulse(),
                readingDevice.getTimestamp()
        );

    }
}
