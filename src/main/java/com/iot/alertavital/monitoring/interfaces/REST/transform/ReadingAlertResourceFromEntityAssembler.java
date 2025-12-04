package com.iot.alertavital.monitoring.interfaces.REST.transform;

import com.iot.alertavital.monitoring.domain.model.entities.Alert;
import com.iot.alertavital.monitoring.interfaces.REST.resources.GetAllAlertsByDescResponse;

import java.time.Instant;

public class ReadingAlertResourceFromEntityAssembler {
    public static GetAllAlertsByDescResponse toResource(Alert alert) {
        return new GetAllAlertsByDescResponse(
                alert.getId(),
                alert.getDeviceId(),
                alert.getTimestamp()
        );

    }
}
