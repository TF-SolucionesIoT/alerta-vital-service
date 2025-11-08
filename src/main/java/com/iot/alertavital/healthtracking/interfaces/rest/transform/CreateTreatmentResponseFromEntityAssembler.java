package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateTreatmentResponse;

public class CreateTreatmentResponseFromEntityAssembler {
    public static CreateTreatmentResponse toResponse(Treatments treatment) {
        return new CreateTreatmentResponse(
                treatment.getName(),
                treatment.getDescription(),
                treatment.getFrequency(),
                treatment.getDosage(),
                treatment.getStartDate(),
                treatment.getEndDate(),
                treatment.getIsActive()
        );
    }
}

