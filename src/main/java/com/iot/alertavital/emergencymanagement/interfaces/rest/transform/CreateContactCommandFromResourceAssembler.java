package com.iot.alertavital.emergencymanagement.interfaces.rest.transform;

import com.iot.alertavital.emergencymanagement.domain.model.commands.CreateContactCommand;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.CreateContactRequest;

public class CreateContactCommandFromResourceAssembler {
    public static CreateContactCommand toCommand(CreateContactRequest request) {
        return new CreateContactCommand(request.name(),
                request.connection(),
                request.phoneNumber(),
                request.patientId());
    }
}
