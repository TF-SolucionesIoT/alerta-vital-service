package com.iot.alertavital.monitoring.application.internal;

import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllReadingByDescQuery;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceQueryService;
import com.iot.alertavital.monitoring.infrastructure.repositories.ReadingDeviceRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadingDeviceQueryServiceImpl implements ReadingDeviceQueryService {

    private final PatientRepository patientRepository;
    ReadingDeviceRepository readingDeviceRepository;
    AuthenticatedUserProvider authenticatedUserProvider;

    public ReadingDeviceQueryServiceImpl(ReadingDeviceRepository readingDeviceRepository, AuthenticatedUserProvider authenticatedUserProvider, PatientRepository patientRepository) {
        this.readingDeviceRepository = readingDeviceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.patientRepository = patientRepository;
    }


    @Override
    public List<ReadingDevice> handle(GetAllReadingByDescQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByUser_Id(userId).orElseThrow(()-> new IllegalArgumentException("Patient not found"));

        return readingDeviceRepository.findAllByDevice_Patient_IdOrderByCreatedAtDesc(patient.getId());
    }
}
