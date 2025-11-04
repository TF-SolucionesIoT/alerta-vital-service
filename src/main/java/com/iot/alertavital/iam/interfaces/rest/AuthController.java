package com.iot.alertavital.iam.interfaces.rest;

import com.iot.alertavital.iam.application.internal.services.AuthService;
import com.iot.alertavital.iam.interfaces.rest.resources.AuthRequestResource;
import com.iot.alertavital.iam.interfaces.rest.resources.AuthResponseResource;
import com.iot.alertavital.iam.interfaces.rest.resources.RegisterRequestResource;
import com.iot.alertavital.iam.interfaces.rest.resources.RegisterResponseResource;
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


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseResource> register(@RequestBody RegisterRequestResource registerRequestResource) {

        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(registerRequestResource);
        var response = authService.register(signUpCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResultToResponseAssembler.fromResultToResponse(response));
    }


}