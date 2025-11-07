package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.commands.CreateDisturbanceCommand;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceRequest;

public class CreateDisturbanceCommandFromResourceAssembler {
    public static CreateDisturbanceCommand toCommand(CreateDisturbanceRequest request) {
        return new CreateDisturbanceCommand(request.name(),
                request.description(),
                request.severity_level(),
                request.onset_date());
    }
}
