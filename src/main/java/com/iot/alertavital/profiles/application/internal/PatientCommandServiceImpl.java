package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdatePatientCommand;
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

    @Override
    public Optional<Patient> handle(UpdatePatientCommand command) {
        Optional<Patient> patientOptional = patientRepository.findById(command.patientId());

        if (patientOptional.isEmpty()) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }

        Patient patient = patientOptional.get();
        patient.updateBirthday(command.birthday());

        try {
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar el paciente: " + e.getMessage());
        }

        return Optional.of(patient);
    }
}
