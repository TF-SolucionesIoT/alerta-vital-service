package com.iot.alertavital.iam.application.internal.services;

import com.iot.alertavital.iam.application.external.results.AuthResponseResult;
import com.iot.alertavital.iam.application.external.results.RegisterResponseResult;
import com.iot.alertavital.iam.domain.model.commands.SignInCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpCaregiverCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpPatientCommand;

public interface AuthService {

    AuthResponseResult login(SignInCommand command);
    RegisterResponseResult register(SignUpPatientCommand command);
    RegisterResponseResult register(SignUpCaregiverCommand command);

}