package com.iot.alertavital.iam.interfaces.rest.transform;

import com.iot.alertavital.iam.application.external.results.AuthResponseResult;
import com.iot.alertavital.iam.application.external.results.RegisterResponseResult;
import com.iot.alertavital.iam.interfaces.rest.resources.AuthResponseResource;
import com.iot.alertavital.iam.interfaces.rest.resources.RegisterResponseResource;

public class SignUpResultToResponseAssembler {
    public static RegisterResponseResource fromResultToResponse(RegisterResponseResult authResponseResult) {
        return new RegisterResponseResource(authResponseResult.accessToken(), authResponseResult.refreshToken(), authResponseResult.username());
    }
}
