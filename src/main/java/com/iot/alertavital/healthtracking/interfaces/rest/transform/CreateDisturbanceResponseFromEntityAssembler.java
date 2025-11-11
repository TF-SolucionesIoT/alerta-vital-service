package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceResponse;

public class CreateDisturbanceResponseFromEntityAssembler {
    public static CreateDisturbanceResponse toResponse(Disturbance disturbance) {
        return new CreateDisturbanceResponse(
                disturbance.getName(),
                disturbance.getDescription(),
                disturbance.getSeverityLevel(),
                disturbance.getOnsetDate()
        );
    }
}
