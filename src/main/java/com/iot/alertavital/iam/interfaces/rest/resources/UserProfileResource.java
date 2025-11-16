package com.iot.alertavital.iam.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Resource (DTO) para la respuesta del perfil de usuario
 * Incluye información básica del User y datos adicionales de Patient/Caregiver
 */
public record UserProfileResource(
    Long id,
    String username,
    String firstName,
    String lastName,
    String email,
    String gender,
    String phoneNumber,
    LocalDate birthday,
    String typeOfUser
) {
}
