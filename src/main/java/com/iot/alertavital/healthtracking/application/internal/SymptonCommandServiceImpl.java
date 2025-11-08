package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateSymptonCommand;
import com.iot.alertavital.healthtracking.domain.model.commands.DeleteSymptonCommand;
import com.iot.alertavital.healthtracking.domain.services.SymptonCommandService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.SymptonRepository;
import com.iot.alertavital.iam.domain.services.JwtService;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SymptonCommandServiceImpl implements SymptonCommandService {
    SymptonRepository symptonRepository;
    PatientRepository patientRepository;
    AuthenticatedUserProvider authenticatedUserProvider;

    public SymptonCommandServiceImpl(SymptonRepository symptonRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.symptonRepository = symptonRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;

    }


    @Override
    public Optional<Sympton> handle(CreateSymptonCommand command) {
        if (symptonRepository.existsSymptonByNameAndOnsetDate(command.name(), command.onset_date())) {
           throw new IllegalArgumentException("Sympton already exists");
        }


        Long userId = authenticatedUserProvider.getCurrentUserId();

        var patient = patientRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        Sympton sympton;
        if (command.resolution_date() != null) {
            sympton = new Sympton(command.name(), command.description(), command.severity_level(), command.onset_date(), command.resolution_date(), command.category(), patient);
        } else {
            sympton = new Sympton(command.name(), command.description(), command.severity_level(), command.onset_date(), command.category(), patient);
        }


        try {
            symptonRepository.save(sympton);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Sympton could not be saved: " + e.getMessage());
        }

        return Optional.of(sympton);
    }

    @Override
    public void handle(DeleteSymptonCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var sympton = symptonRepository.findSymptonByIdAndPatient_User_Id(command.id(), userId).orElseThrow(() -> new IllegalArgumentException("Sympton does not exist"));

        try {
            symptonRepository.delete(sympton);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Sympton could not be deleted: " + e.getMessage());
        }
    }
}

