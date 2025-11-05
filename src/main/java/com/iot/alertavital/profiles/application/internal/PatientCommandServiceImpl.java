package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.services.PatientCommandService;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    PatientRepository patientRepository;
    public PatientCommandServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(CreatePatientCommand command) {
        var patient = new Patient(command);

        try {
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new IllegalArgumentException("un error ha ocurrido mientras se guarda la entidad: " + e.getMessage());
        }
        return Optional.of(patient);
    }
}
