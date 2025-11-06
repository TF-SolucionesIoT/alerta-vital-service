package com.iot.alertavital.iam.interfaces.rest;

import com.iot.alertavital.iam.domain.model.commands.UpdateInformationCommand;
import com.iot.alertavital.iam.domain.model.commands.UpdatePasswordCommand;
import com.iot.alertavital.iam.domain.services.UserCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserCommandService userCommandService;

    public UserController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(
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

    @PutMapping("/{userId}/information")
    public ResponseEntity<String> updateInformation(
            @PathVariable Long userId,
            @RequestBody UpdateInformationRequest request) {

        try {
            var command = new UpdateInformationCommand(userId, request.firstName(), request.lastName(), request.email(), request.username());
            userCommandService.handle(command);
            return ResponseEntity.ok("Información actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // DTOs internos
    public record UpdatePasswordRequest(String currentPassword, String newPassword) {}

    public record UpdateInformationRequest(String firstName, String lastName, String email, String username) {}
}
