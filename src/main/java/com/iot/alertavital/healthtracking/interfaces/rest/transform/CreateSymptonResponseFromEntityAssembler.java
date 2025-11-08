package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateSymptonResponse;

public class CreateSymptonResponseFromEntityAssembler {
    public static CreateSymptonResponse toResponse(Sympton sympton) {
        return new CreateSymptonResponse(
                sympton.getName(),
                sympton.getDescription(),
                sympton.getSeverityLevel(),
                sympton.getOnsetDate(),
                sympton.getCategory(),
                sympton.getResolutionDate()
        );
    }
}

