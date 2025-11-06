package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateCaregiverCommand;
import com.iot.alertavital.profiles.domain.services.CaregiverCommandService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaregiverCommandServiceImpl implements CaregiverCommandService {
    private final CaregiverRepository caregiverRepository;

    public CaregiverCommandServiceImpl(CaregiverRepository caregiverRepository) {
        this.caregiverRepository = caregiverRepository;
    }

    @Override
    public Optional<Caregiver> handle(CreateCaregiverCommand command) {
        var caregiver = new Caregiver(command);

        try {
            caregiverRepository.save(caregiver);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return Optional.of(caregiver);
    }

    @Override
    public Optional<Caregiver> handle(UpdateCaregiverCommand command) {
        Optional<Caregiver> caregiverOptional = caregiverRepository.findById(command.caregiverId());

        if (caregiverOptional.isEmpty()) {
            throw new IllegalArgumentException("Cuidador no encontrado");
        }

        Caregiver caregiver = caregiverOptional.get();
        caregiver.updatePhoneNumber(command.phoneNumber());

        try {
            caregiverRepository.save(caregiver);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar el cuidador: " + e.getMessage());
        }

        return Optional.of(caregiver);
    }
}
