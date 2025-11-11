package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllDisturbanceByPatientResponse;

public class DisturbanceResourceFromEntityAssembler {
    public static GetAllDisturbanceByPatientResponse toResource(Disturbance disturbance){
        return new GetAllDisturbanceByPatientResponse(
                disturbance.getId(),
                disturbance.getName(),
                disturbance.getDescription(),
                disturbance.getSeverityLevel(),
                disturbance.getOnsetDate()
        );

    }
}
