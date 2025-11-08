package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.commands.CreateTreatmentCommand;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateTreatmentRequest;

public class CreateTreatmentCommandFromResourceAssembler {
    public static CreateTreatmentCommand toCommand(CreateTreatmentRequest request) {
        return new CreateTreatmentCommand(request.name(),
                request.description(),
                request.frequency(),
                request.dosage(),
                request.startDate(),
                request.endDate(),
                request.isActive());
    }
}

