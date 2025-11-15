package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.queries.GetPatientHealthRecordsByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.PatientHealthRecordsQueryService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.DisturbanceRepository;
import com.iot.alertavital.healthtracking.infrastructure.repositories.SymptonRepository;
import com.iot.alertavital.healthtracking.infrastructure.repositories.TreatmentsRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PatientHealthRecordsQueryServiceImpl implements PatientHealthRecordsQueryService {

    private final DisturbanceRepository disturbanceRepository;
    private final SymptonRepository symptonRepository;
    private final TreatmentsRepository treatmentsRepository;
    private final CaregiverPatientAccessRepository accessRepository;
    private final CaregiverRepository caregiverRepository;
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public PatientHealthRecordsQueryServiceImpl(
            DisturbanceRepository disturbanceRepository,
            SymptonRepository symptonRepository,
            TreatmentsRepository treatmentsRepository,
            CaregiverPatientAccessRepository accessRepository,
            CaregiverRepository caregiverRepository,
            PatientRepository patientRepository,
            AuthenticatedUserProvider authenticatedUserProvider) {
        this.disturbanceRepository = disturbanceRepository;
        this.symptonRepository = symptonRepository;
        this.treatmentsRepository = treatmentsRepository;
        this.accessRepository = accessRepository;
        this.caregiverRepository = caregiverRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Object handle(GetPatientHealthRecordsByPatientIdQuery query) {
        Long currentUserId = authenticatedUserProvider.getCurrentUserId();
        String userType = authenticatedUserProvider.getCurrentUserType();

        if (!Objects.equals(userType, "CAREGIVER")) {
            throw new IllegalArgumentException("Only caregivers can access patient health records");
        }

        var caregiver = caregiverRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        boolean hasAccess = accessRepository.existsByCaregiverIdAndPatientId(caregiver.getId(), query.patientId());

        if (!hasAccess) {
            throw new IllegalArgumentException("Caregiver does not have access to this patient");
        }

        var patient = patientRepository.findById(query.patientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        var disturbances = disturbanceRepository.findAllByPatient_User_IdOrderByOnsetDateDesc(patient.getUser().getId());
        var symptons = symptonRepository.findAllByPatient_User_IdOrderByOnsetDateDesc(patient.getUser().getId());
        var treatments = treatmentsRepository.findAllByPatient_User_IdOrderByStartDateDesc(patient.getUser().getId());

        Map<String, Object> records = new HashMap<>();
        records.put("patientId", patient.getId());
        records.put("patientName", patient.getUser().fullName());
        records.put("disturbances", disturbances);
        records.put("symptons", symptons);
        records.put("treatments", treatments);

        return records;
    }
}
