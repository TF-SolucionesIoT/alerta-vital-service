package com.iot.alertavital.iam.interfaces.rest.transform;

import com.iot.alertavital.iam.domain.model.commands.SignInCommand;
import com.iot.alertavital.iam.interfaces.rest.resources.AuthRequestResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(AuthRequestResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}