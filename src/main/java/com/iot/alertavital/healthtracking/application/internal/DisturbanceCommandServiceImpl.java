package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateDisturbanceCommand;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceCommandService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.DisturbanceRepository;
import com.iot.alertavital.iam.domain.services.JwtService;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisturbanceCommandServiceImpl implements DisturbanceCommandService {
    DisturbanceRepository disturbanceRepository;
    PatientRepository patientRepository;
    AuthenticatedUserProvider authenticatedUserProvider;

    public DisturbanceCommandServiceImpl(DisturbanceRepository disturbanceRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.disturbanceRepository = disturbanceRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;

    }


    @Override
    public Optional<Disturbance> handle(CreateDisturbanceCommand command) {
        if (disturbanceRepository.existsDisturbanceByNameAndOnsetDate(command.name(), command.onset_date())) {
           throw new IllegalArgumentException("Disturbance already exists");
        }


        Long userId = authenticatedUserProvider.getCurrentUserId();

        var patient = patientRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        Disturbance disturbance = new Disturbance(command.name(), command.description(), command.severity_level(), command.onset_date(), patient);


        try {
            disturbanceRepository.save(disturbance);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Disturbance could not be saved: " + e.getMessage());
        }

        return Optional.of(disturbance);
    }
}
