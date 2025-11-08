package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.commands.CreateSymptonCommand;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateSymptonRequest;

public class CreateSymptonCommandFromResourceAssembler {
    public static CreateSymptonCommand toCommand(CreateSymptonRequest request) {
        return new CreateSymptonCommand(request.name(),
                request.description(),
                request.severity_level(),
                request.onset_date(),
                request.category(),
                request.resolution_date());
    }
}

