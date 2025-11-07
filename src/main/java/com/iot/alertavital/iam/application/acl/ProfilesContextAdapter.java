package com.iot.alertavital.iam.application.acl;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.interfaces.ACL.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Adaptador Anti-Corrupción para el contexto de Profiles actualizado.
 *
 * REFACTORIZACIÓN v2.0: Este adaptador ha sido actualizado para trabajar con
 * la nueva arquitectura unificada de profiles. Ahora usa un solo agregado Profile
 * que maneja tanto patients como caregivers según el UserType.
 *
 * Propósito:
 * - Mantener compatibilidad con el flujo de registro existente
 * - Delegar al módulo Profiles unificado
 * - Servir como Anti-Corruption Layer entre IAM y Profiles
 *
 * @since 2.0.0 - Unificación de perfiles de usuario en Profiles BC
 */
@Service
public class ProfilesContextAdapter {

    private final ProfilesContextFacade profilesContextFacade;

    public ProfilesContextAdapter(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    /**
     * Crea un perfil de paciente para el usuario.
     * @param user Usuario para el cual crear el perfil
     * @param birthday Fecha de nacimiento del paciente
     */
    public void createPatientForUser(User user, LocalDate birthday) {
        profilesContextFacade.createPatientProfile(user, birthday);
    }

    /**
     * Crea un perfil de cuidador para el usuario.
     * @param user Usuario para el cual crear el perfil
     * @param phoneNumber Número de teléfono del cuidador
     */
    public void createCaregiverForUser(User user, String phoneNumber) {
        profilesContextFacade.createCaregiverProfile(user, phoneNumber);
    }
}