package com.iot.alertavital.profiles.interfaces.ACL;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.services.CaregiverCommandService;
import com.iot.alertavital.profiles.domain.services.PatientCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilesContextFacade {

    private final PatientCommandService patientCommandService;
    private final CaregiverCommandService caregiverCommandService;

    public ProfilesContextFacade(PatientCommandService patientCommandService, CaregiverCommandService caregiverCommandService) {
        this.patientCommandService = patientCommandService;
        this.caregiverCommandService = caregiverCommandService;
    }

    public void createPatient(CreatePatientCommand command) {
        patientCommandService.handle(command);
    }

    public void createCaregiver(CreateCaregiverCommand command){
        caregiverCommandService.handle(command);
    }
    public Optional<Patient> findPatientByUserId(Long userId) {
        // consulta y retorno del perfil
        return Optional.empty();
    }
}