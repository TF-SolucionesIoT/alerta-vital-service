package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllTreatmentByPatientResponse;

public class TreatmentResourceFromEntityAssembler {
    public static GetAllTreatmentByPatientResponse toResource(Treatments treatment){
        return new GetAllTreatmentByPatientResponse(
                treatment.getId(),
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

