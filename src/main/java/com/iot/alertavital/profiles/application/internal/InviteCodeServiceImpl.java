package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.domain.model.aggregates.CaregiverPatientAccess;
import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;
import com.iot.alertavital.profiles.domain.services.InviteCodeService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientInviteCodeRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {

    private final PatientInviteCodeRepository inviteRepo;
    private final CaregiverPatientAccessRepository accessRepo;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final PatientRepository patientRepo;
    private final CaregiverRepository caregiverRepo;

    public InviteCodeServiceImpl(PatientInviteCodeRepository inviteRepo,
                                 CaregiverPatientAccessRepository accessRepo, AuthenticatedUserProvider authenticatedUserProvider, PatientRepository patientRepo, CaregiverRepository caregiverRepo) {
        this.inviteRepo = inviteRepo;
        this.accessRepo = accessRepo;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.patientRepo = patientRepo;
        this.caregiverRepo = caregiverRepo;
    }


    @Override
    public PatientInviteCode generateCode() {

        Long userId = authenticatedUserProvider.getCurrentUserId();

        if (!Objects.equals(authenticatedUserProvider.getCurrentUserType(), "PATIENT")){
            throw new IllegalStateException("Only PATIENT users are allowed to invite code");
        }

        var patient = patientRepo.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        PatientInviteCode invite = new PatientInviteCode();
        invite.setPatientId(patient.getId());
        invite.setCode(code);
        invite.setExpiresAt(LocalDateTime.now().plusHours(24));
        invite.setUsed(false);

        return inviteRepo.save(invite);
    }


    @Override
    public String useCode(String code) {

        Long userId = authenticatedUserProvider.getCurrentUserId();

        if (!Objects.equals(authenticatedUserProvider.getCurrentUserType(), "CAREGIVER")){
            throw new IllegalStateException("Only CAREGIVER users are allowed to use code");
        }


        var caregiver = caregiverRepo.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Caregiver does not exist"));


        PatientInviteCode invite = inviteRepo.findByCodeAndUsedFalse(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired code."));

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expired.");
        }

        if (accessRepo.existsByCaregiverIdAndPatientId(caregiver.getId(), invite.getPatientId())) {
            throw new RuntimeException("Access already exists.");
        }

        // Obtener información del paciente para el mensaje de respuesta
        var patient = patientRepo.findById(invite.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));
        
        var patientUser = patient.getUser();
        String patientName = patientUser.getName().firstName() + " " + patientUser.getName().lastName();

        CaregiverPatientAccess access = new CaregiverPatientAccess();
        access.setCaregiverId(caregiver.getId());
        access.setPatientId(invite.getPatientId());
        access.setGrantedDate(LocalDateTime.now());
        accessRepo.save(access);

        invite.setUsed(true);
        inviteRepo.save(invite);

        return "Enlazado exitosamente con el paciente: " + patientName;
    }

    @Override
    public List<Map<String, Object>> getLinkedPatients() {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        if (!Objects.equals(authenticatedUserProvider.getCurrentUserType(), "CAREGIVER")){
            throw new IllegalStateException("Only CAREGIVER users are allowed to view linked patients");
        }

        var caregiver = caregiverRepo.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Caregiver does not exist"));

        List<CaregiverPatientAccess> accesses = accessRepo.findByCaregiverId(caregiver.getId());

        return accesses.stream().map(access -> {
            var patient = patientRepo.findById(access.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
            var patientUser = patient.getUser();
            
            Map<String, Object> patientInfo = new HashMap<>();
            patientInfo.put("patientId", patient.getId());
            patientInfo.put("firstName", patientUser.getName().firstName());
            patientInfo.put("lastName", patientUser.getName().lastName());
            patientInfo.put("username", patientUser.getUsername());
            patientInfo.put("email", patientUser.getEmail().address());
            patientInfo.put("grantedDate", access.getGrantedDate());
            
            return patientInfo;
        }).collect(Collectors.toList());
    }
}
