package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateTreatmentCommand;
import com.iot.alertavital.healthtracking.domain.services.TreatmentCommandService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.TreatmentsRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreatmentCommandServiceImpl implements TreatmentCommandService {
    TreatmentsRepository treatmentsRepository;
    PatientRepository patientRepository;
    AuthenticatedUserProvider authenticatedUserProvider;

    public TreatmentCommandServiceImpl(TreatmentsRepository treatmentsRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.treatmentsRepository = treatmentsRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Optional<Treatments> handle(CreateTreatmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        var patient = patientRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        Treatments treatment = new Treatments(command.name(), command.description(), command.frequency(), command.dosage(), command.startDate(), command.endDate(), command.isActive(), patient);

        try {
            treatmentsRepository.save(treatment);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Treatment could not be saved: " + e.getMessage());
        }

        return Optional.of(treatment);
    }
}

