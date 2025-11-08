package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllSymptonByPatientResponse;

public class SymptonResourceFromEntityAssembler {
    public static GetAllSymptonByPatientResponse toResource(Sympton sympton){
        return new GetAllSymptonByPatientResponse(
                sympton.getId(),
                sympton.getName(),
                sympton.getDescription(),
                sympton.getSeverityLevel(),
                sympton.getOnsetDate(),
                sympton.getCategory(),
                sympton.getResolutionDate()
        );

    }
}

