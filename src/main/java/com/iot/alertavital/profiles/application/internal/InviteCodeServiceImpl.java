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
import java.util.UUID;

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
        var caregiver = caregiverRepo.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));


        PatientInviteCode invite = inviteRepo.findByCodeAndUsedFalse(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired code."));

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expired.");
        }

        if (accessRepo.existsByCaregiverIdAndPatientId(caregiver.getId(), invite.getPatientId())) {
            throw new RuntimeException("Access already exists.");
        }

        CaregiverPatientAccess access = new CaregiverPatientAccess();
        access.setCaregiverId(caregiver.getId());
        access.setPatientId(invite.getPatientId());
        access.setGrantedDate(LocalDateTime.now());
        accessRepo.save(access);

        invite.setUsed(true);
        inviteRepo.save(invite);

        return "Access granted successfully.";
    }
}
