package com.iot.alertavital.iam.interfaces.rest.transform;


import com.iot.alertavital.iam.domain.model.commands.SignUpCaregiverCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpPatientCommand;
import com.iot.alertavital.iam.interfaces.rest.resources.CaregiverRegisterRequestResource;
import com.iot.alertavital.iam.interfaces.rest.resources.PatientRegisterRequestResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpPatientCommand toCommandFromPatientResource(PatientRegisterRequestResource resource) {
        return new SignUpPatientCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.gender(),
                resource.username(),
                resource.password(),
                resource.birthday()
        );
    }

    public static SignUpCaregiverCommand toCommandFromCaregiverResource(CaregiverRegisterRequestResource resource) {
        return new SignUpCaregiverCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.gender(),
                resource.username(),
                resource.password(),
                resource.phoneNumber()
        );
    }
}