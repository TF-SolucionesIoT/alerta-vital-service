package com.iot.alertavital.iam.interfaces.rest.transform;


import com.iot.alertavital.iam.domain.model.commands.SignUpCommand;
import com.iot.alertavital.iam.interfaces.rest.resources.RegisterRequestResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(RegisterRequestResource resource) {
        return new SignUpCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.gender(),
                resource.username(),
                resource.password()
        );
    }
}