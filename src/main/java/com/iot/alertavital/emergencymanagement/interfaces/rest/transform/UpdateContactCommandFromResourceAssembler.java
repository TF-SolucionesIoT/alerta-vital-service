package com.iot.alertavital.emergencymanagement.interfaces.rest.transform;

import com.iot.alertavital.emergencymanagement.domain.model.commands.UpdateContactCommand;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.UpdateContactRequest;

public class UpdateContactCommandFromResourceAssembler {
    public static UpdateContactCommand toCommand(Long id, UpdateContactRequest request) {
        return new UpdateContactCommand(
                id,
                request.name(),
                request.connection(),
                request.phoneNumber());
    }
}
