package com.iot.alertavital.iam.interfaces.rest.transform;

import com.iot.alertavital.iam.application.external.results.AuthResponseResult;
import com.iot.alertavital.iam.interfaces.rest.resources.AuthResponseResource;

public class SignInResultToResponseAssembler {
    public static AuthResponseResource fromResultToResponse(AuthResponseResult authResponseResult) {
        return new AuthResponseResource(authResponseResult.accessToken(), authResponseResult.refreshToken());
    }
}
