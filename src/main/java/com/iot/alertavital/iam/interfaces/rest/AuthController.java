package com.iot.alertavital.iam.interfaces.rest;

import com.iot.alertavital.iam.application.internal.services.AuthService;
import com.iot.alertavital.iam.interfaces.rest.resources.*;
import com.iot.alertavital.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignInResultToResponseAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignUpResultToResponseAssembler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authCommandService) {
        this.authService = authCommandService;
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user found"),
            @ApiResponse(responseCode = "404", description = "user not found")
    })
    public ResponseEntity<AuthResponseResource> login(@RequestBody AuthRequestResource authRequestResource){

        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(authRequestResource);
        var response = authService.login(signInCommand);
        return ResponseEntity.ok(SignInResultToResponseAssembler.fromResultToResponse(response));
    }


    @PostMapping("/register/patient")
    public ResponseEntity<RegisterResponseResource> register(@RequestBody PatientRegisterRequestResource patientRegisterRequestResource) {

        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromPatientResource(patientRegisterRequestResource);
        var response = authService.register(signUpCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResultToResponseAssembler.fromResultToResponse(response));
    }

    @PostMapping("/register/caregiver")
    public ResponseEntity<RegisterResponseResource> register(@RequestBody CaregiverRegisterRequestResource caregiverRegisterRequestResource) {

        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromCaregiverResource(caregiverRegisterRequestResource);
        var response = authService.register(signUpCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResultToResponseAssembler.fromResultToResponse(response));
    }


}