package com.iot.alertavital.profiles.interfaces.ACL;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdatePatientCommand;
import com.iot.alertavital.profiles.domain.services.CaregiverCommandService;
import com.iot.alertavital.profiles.domain.services.CaregiverQueryService;
import com.iot.alertavital.profiles.domain.services.PatientCommandService;
import com.iot.alertavital.profiles.domain.services.PatientQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilesContextFacade {

    private final PatientCommandService patientCommandService;
    private final CaregiverCommandService caregiverCommandService;
    private final PatientQueryService patientQueryService;
    private final CaregiverQueryService caregiverQueryService;

    public ProfilesContextFacade(PatientCommandService patientCommandService,
                               CaregiverCommandService caregiverCommandService,
                               PatientQueryService patientQueryService,
                               CaregiverQueryService caregiverQueryService) {
        this.patientCommandService = patientCommandService;
        this.caregiverCommandService = caregiverCommandService;
        this.patientQueryService = patientQueryService;
        this.caregiverQueryService = caregiverQueryService;
    }

    public void createPatient(CreatePatientCommand command) {
        patientCommandService.handle(command);
    }

    public void createCaregiver(CreateCaregiverCommand command){
        caregiverCommandService.handle(command);
    }

    public void updatePatient(UpdatePatientCommand command) {
        patientCommandService.handle(command);
    }

    public void updateCaregiver(UpdateCaregiverCommand command){
        caregiverCommandService.handle(command);
    }

    public Optional<Patient> findPatientByUserId(Long userId) {
        return patientQueryService.findByUserId(userId);
    }

    public Optional<Caregiver> findCaregiverByUserId(Long userId) {
        return caregiverQueryService.findByUserId(userId);
    }

    public Optional<Patient> findPatientById(Long patientId) {
        return patientQueryService.findById(patientId);
    }

    public Optional<Caregiver> findCaregiverById(Long caregiverId) {
        return caregiverQueryService.findById(caregiverId);
    }
}