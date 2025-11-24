package com.iot.alertavital.profiles.interfaces.REST.transform;

import com.iot.alertavital.profiles.domain.model.commands.ChangePasswordCommand;
import com.iot.alertavital.profiles.interfaces.REST.resources.ChangePasswordRequest;

public class ProfileConfigCommandFromResourceAssembler {

    public static ChangePasswordCommand toCommand(ChangePasswordRequest request) {
        return new ChangePasswordCommand(
                request.password(),
                request.newPassword()
        );
    }
}
