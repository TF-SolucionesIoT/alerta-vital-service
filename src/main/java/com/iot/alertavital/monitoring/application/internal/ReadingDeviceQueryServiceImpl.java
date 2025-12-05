package com.iot.alertavital.monitoring.application.internal;

import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.monitoring.domain.model.entities.Alert;
import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllAlertsByDescQuery;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllReadingByDescQuery;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceQueryService;
import com.iot.alertavital.monitoring.infrastructure.repositories.AlertRepository;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import com.iot.alertavital.monitoring.infrastructure.repositories.ReadingDeviceRepository;
import com.iot.alertavital.profiles.domain.model.aggregates.CaregiverPatientAccess;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReadingDeviceQueryServiceImpl implements ReadingDeviceQueryService {

    private final PatientRepository patientRepository;
    private final ReadingDeviceRepository readingDeviceRepository;
    private final DeviceRepository deviceRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final AlertRepository alertRepository;
    private final CaregiverPatientAccessRepository caregiverPatientAccessRepository;
    private final CaregiverRepository caregiverRepository;

    public ReadingDeviceQueryServiceImpl(ReadingDeviceRepository readingDeviceRepository, AuthenticatedUserProvider authenticatedUserProvider, PatientRepository patientRepository, DeviceRepository deviceRepository, AlertRepository alertRepository, CaregiverPatientAccessRepository caregiverPatientAccessRepository, CaregiverRepository caregiverRepository) {
        this.readingDeviceRepository = readingDeviceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.patientRepository = patientRepository;
        this.deviceRepository = deviceRepository;
        this.alertRepository = alertRepository;
        this.caregiverPatientAccessRepository = caregiverPatientAccessRepository;
        this.caregiverRepository = caregiverRepository;
    }


    @Override
    public List<ReadingDevice> handle(GetAllReadingByDescQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        if (Objects.equals(authenticatedUserProvider.getCurrentUserType(), "PATIENT")) {
            var patient = patientRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
            return readingDeviceRepository.findAllByDevice_Patient_IdOrderByCreatedAtDesc(patient.getId());
        }
        else if (Objects.equals(authenticatedUserProvider.getCurrentUserType(), "CAREGIVER")) {
            var caregiver = caregiverRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

            var accessList = caregiverPatientAccessRepository.findByCaregiverId(caregiver.getId());
            if (accessList.isEmpty()) {
                throw new IllegalArgumentException("No patients assigned to this caregiver");
            }
            var patientIds = accessList.stream().map(CaregiverPatientAccess::getPatientId).toList();
            return readingDeviceRepository.findAllByDevice_Patient_IdInOrderByCreatedAtDesc(patientIds);
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }

    @Override
    public List<Alert> handle(GetAllAlertsByDescQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByUser_Id(userId).orElseThrow(()-> new IllegalArgumentException("Patient not found"));

        var device = deviceRepository.findByPatient_Id(patient.getId());

        if (device == null) {
            throw new IllegalArgumentException("Device not found for the patient");
        }

        return alertRepository.findAllByDeviceIdOrderByTimestampDesc(device.getDeviceId());
    }
}
