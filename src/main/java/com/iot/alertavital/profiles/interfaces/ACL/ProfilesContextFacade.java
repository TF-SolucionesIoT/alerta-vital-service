package com.iot.alertavital.profiles.interfaces.ACL;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateProfileCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.profiles.domain.services.ProfileCommandService;
import com.iot.alertavital.profiles.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Fachada unificada para el contexto de Profiles.
 * Ahora maneja un solo agregado Profile que puede ser tanto Patient como Caregiver.
 */
@Service
public class ProfilesContextFacade {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacade(ProfileCommandService profileCommandService,
                               ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    // Métodos de creación unificados
    public void createPatientProfile(User user, LocalDate birthday) {
        var command = new CreateProfileCommand(user, UserType.PATIENT, null, birthday);
        profileCommandService.handle(command);
    }

    public void createCaregiverProfile(User user, String phoneNumber) {
        var command = new CreateProfileCommand(user, UserType.CAREGIVER, phoneNumber, null);
        profileCommandService.handle(command);
    }

    // Métodos de actualización unificados
    public void updatePatientProfile(Long profileId, LocalDate birthday) {
        var command = new UpdateProfileCommand(profileId, null, birthday);
        profileCommandService.handle(command);
    }

    public void updateCaregiverProfile(Long profileId, String phoneNumber) {
        var command = new UpdateProfileCommand(profileId, phoneNumber, null);
        profileCommandService.handle(command);
    }

    // Métodos de consulta unificados
    public Optional<Profile> findProfileByUserId(Long userId) {
        return profileQueryService.findByUserId(userId);
    }

    public Optional<Profile> findProfileById(Long profileId) {
        return profileQueryService.findById(profileId);
    }

    // Métodos de compatibilidad (mantienen la interfaz anterior)
    public Optional<Profile> findPatientByUserId(Long userId) {
        return profileQueryService.findByUserId(userId)
            .filter(Profile::isPatient);
    }

    public Optional<Profile> findCaregiverByUserId(Long userId) {
        return profileQueryService.findByUserId(userId)
            .filter(Profile::isCaregiver);
    }

    public List<Profile> findAllPatients() {
        return profileQueryService.findAllPatients();
    }

    public List<Profile> findAllCaregivers() {
        return profileQueryService.findAllCaregivers();
    }

    public List<Profile> findAllProfiles() {
        return profileQueryService.findAll();
    }
}
