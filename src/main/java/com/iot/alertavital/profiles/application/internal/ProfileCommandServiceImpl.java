package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateProfileCommand;
import com.iot.alertavital.profiles.domain.services.ProfileCommandService;
import com.iot.alertavital.profiles.infrastructure.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    
    private final ProfileRepository profileRepository;
    
    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        try {
            // Verificar que no exista ya un perfil para este usuario
            if (profileRepository.findByUserId(command.user().getId()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un perfil para este usuario");
            }
            
            // Crear el perfil según el tipo
            Profile profile = new Profile(
                command.user(),
                command.userType(),
                command.phoneNumber(),
                command.birthday()
            );
            
            profileRepository.save(profile);
            return Optional.of(profile);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el perfil: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        try {
            Optional<Profile> profileOptional = profileRepository.findById(command.profileId());
            if (profileOptional.isEmpty()) {
                throw new IllegalArgumentException("Perfil no encontrado");
            }
            
            Profile profile = profileOptional.get();
            
            // Actualizar según el tipo de usuario
            profile.updateProfileInformation(command.phoneNumber(), command.birthday());
            
            profileRepository.save(profile);
            return Optional.of(profile);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar el perfil: " + e.getMessage());
        }
    }
}
