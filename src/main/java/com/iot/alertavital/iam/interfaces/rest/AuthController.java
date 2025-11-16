package com.iot.alertavital.iam.interfaces.rest;

import com.iot.alertavital.iam.application.internal.services.AuthService;
import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.infrastructure.repositories.UserRepository;
import com.iot.alertavital.iam.interfaces.rest.resources.*;
import com.iot.alertavital.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignInResultToResponseAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.SignUpResultToResponseAssembler;
import com.iot.alertavital.iam.interfaces.rest.transform.UserProfileResourceAssembler;
import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final CaregiverRepository caregiverRepository;

    public AuthController(
            AuthService authCommandService,
            UserRepository userRepository,
            PatientRepository patientRepository,
            CaregiverRepository caregiverRepository
    ) {
        this.authService = authCommandService;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.caregiverRepository = caregiverRepository;
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

    /**
     * Endpoint para obtener el perfil del usuario autenticado
     * Devuelve información completa del usuario incluyendo datos de Patient o Caregiver
     */
    @GetMapping("/profile/me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserProfileResource> getCurrentUserProfile(Authentication authentication) {
        // Obtener el ID del usuario desde el token JWT (almacenado en el subject)
        String userId = authentication.getName();
        
        // Buscar el usuario en la base de datos
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Buscar el Patient o Caregiver asociado
        Patient patient = patientRepository.findByUser_Id(user.getId()).orElse(null);
        Caregiver caregiver = caregiverRepository.findByUser_Id(user.getId()).orElse(null);
        
        // Transformar a Resource y devolver
        return ResponseEntity.ok(
            UserProfileResourceAssembler.toResourceFromEntity(user, patient, caregiver)
        );
    }

}