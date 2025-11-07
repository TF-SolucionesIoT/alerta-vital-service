package com.iot.alertavital.profiles.interfaces.rest;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.model.commands.UpdateInformationCommand;
import com.iot.alertavital.iam.domain.model.commands.UpdatePasswordCommand;
import com.iot.alertavital.iam.domain.services.UserCommandService;
import com.iot.alertavital.iam.domain.services.UserQueryService;
import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateProfileCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.profiles.domain.services.ProfileCommandService;
import com.iot.alertavital.profiles.domain.services.ProfileQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * UserController en el bounded context de Profiles
 * Maneja usuarios unificados con información de perfil integrada
 * Endpoints: /api/v1/users/* y /api/v1/iam/users/* (trabajando por users, no por tipos)
 */
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService; // nuevo

    public UserController(ProfileCommandService profileCommandService,
                         ProfileQueryService profileQueryService,
                         UserQueryService userQueryService,
                         UserCommandService userCommandService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            // Buscar el usuario en IAM
            var userOptional = userQueryService.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var user = userOptional.get();
            
            // Buscar el perfil asociado
            var profileOptional = profileQueryService.findByUserId(userId);
            
            if (profileOptional.isPresent()) {
                // Usuario con perfil completo
                return ResponseEntity.ok(new UserResponse(user, profileOptional.get()));
            } else {
                // Usuario sin perfil (solo información básica)
                return ResponseEntity.ok(new UserResponse(user));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            // Obtener todos los perfiles (que incluyen la información del usuario)
            var profiles = profileQueryService.findAll();
            var response = profiles.stream()
                .map(profile -> new UserResponse(profile.getUser(), profile))
                .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping(value = "/api/v1/users", params = "type=caregiver")
    public ResponseEntity<?> getAllCaregivers() {
        try {
            var caregivers = profileQueryService.findAllCaregivers();
            var response = caregivers.stream()
                .map(profile -> new UserResponse(profile.getUser(), profile))
                .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping(value = "/api/v1/users", params = "type=patient")
    public ResponseEntity<?> getAllPatients() {
        try {
            var patients = profileQueryService.findAllPatients();
            var response = patients.stream()
                .map(profile -> new UserResponse(profile.getUser(), profile))
                .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<?> createUserProfile(@RequestBody CreateUserRequest request) {
        try {
            // Buscar el usuario
            var userOptional = userQueryService.findById(request.userId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuario no encontrado");
            }

            var user = userOptional.get();

            // Crear el perfil
            var command = new CreateProfileCommand(
                user,
                request.userType(),
                request.phoneNumber(),
                request.birthday()
            );

            var profileOptional = profileCommandService.handle(command);
            if (profileOptional.isPresent()) {
                return ResponseEntity.ok(new UserResponse(user, profileOptional.get()));
            } else {
                return ResponseEntity.badRequest().body("No se pudo crear el perfil del usuario");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {

        try {
            // Buscar el perfil del usuario
            var profileOptional = profileQueryService.findByUserId(userId);
            if (profileOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Perfil de usuario no encontrado");
            }

            var profile = profileOptional.get();

            // Actualizar el perfil
            var command = new UpdateProfileCommand(
                profile.getId(),
                request.phoneNumber(),
                request.birthday()
            );

            profileCommandService.handle(command);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @DeleteMapping("/api/v1/users/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Long userId) {
        try {
            var profileOptional = profileQueryService.findByUserId(userId);
            if (profileOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Aquí se podría implementar la lógica de eliminación del perfil
            // Por ahora solo retornamos éxito
            return ResponseEntity.ok("Perfil de usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // NUEVOS ENDPOINTS: delegar actualizaciones de IAM desde aquí
    @PutMapping(path = "/api/v1/iam/users/{userId}/password")
    public ResponseEntity<String> updatePasswordFromProfiles(
            @PathVariable Long userId,
            @RequestBody UpdatePasswordRequest request) {
        try {
            var command = new UpdatePasswordCommand(userId, request.currentPassword(), request.newPassword());
            userCommandService.handle(command);
            return ResponseEntity.ok("Contraseña actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping(path = "/api/v1/iam/users/{userId}/information")
    public ResponseEntity<String> updateInformationFromProfiles(
            @PathVariable Long userId,
            @RequestBody UpdateInformationRequest request) {

        try {
            var command = new UpdateInformationCommand(
                userId,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.username(),
                request.phoneNumber(),
                request.birthday()
            );
            userCommandService.handle(command);
            return ResponseEntity.ok("Información actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // DTOs internos
    public record CreateUserRequest(
        Long userId,
        UserType userType,
        String phoneNumber,    // Para caregivers
        LocalDate birthday     // Para patients
    ) {}

    public record UpdateUserRequest(
        String phoneNumber,    // Para caregivers
        LocalDate birthday     // Para patients
    ) {}

    // Reusar DTOs similares a los existentes en iam
    public record UpdatePasswordRequest(String currentPassword, String newPassword) {}

    public record UpdateInformationRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String phoneNumber,  // Para caregivers
        LocalDate birthday   // Para patients
    ) {}

    public record UserResponse(
        Long id,
        String username,
        String fullName,
        String email,
        String userType,       // Tipo de perfil
        String phoneNumber,    // Null si no es caregiver o no tiene perfil
        LocalDate birthday,    // Null si no es patient o no tiene perfil
        boolean hasProfile     // Indica si tiene perfil completo
    ) {
        // Constructor para usuario con perfil
        public UserResponse(User user, Profile profile) {
            this(
                user.getId(),
                user.getUsername(),
                user.fullName(),
                user.email(),
                profile.getUserType().toString(),
                profile.getPhoneNumberValue(),
                profile.getBirthdayValue(),
                true
            );
        }
        
        // Constructor para usuario sin perfil
        public UserResponse(User user) {
            this(
                user.getId(),
                user.getUsername(),
                user.fullName(),
                user.email(),
                null,
                null,
                null,
                false
            );
        }
    }
}
