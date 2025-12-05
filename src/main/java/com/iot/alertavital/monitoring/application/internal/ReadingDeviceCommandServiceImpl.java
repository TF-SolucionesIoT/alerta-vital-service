package com.iot.alertavital.monitoring.application.internal;

import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.monitoring.domain.model.commands.CreateReadingDeviceCommand;
import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceCommandService;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import com.iot.alertavital.monitoring.infrastructure.repositories.ReadingDeviceRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ReadingDeviceCommandServiceImpl implements ReadingDeviceCommandService {
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final DeviceRepository deviceRepository;
    private final PatientRepository patientRepository;
    private final ReadingDeviceRepository readingDeviceRepository;
    public ReadingDeviceCommandServiceImpl(AuthenticatedUserProvider authenticatedUserProvider, DeviceRepository deviceRepository, PatientRepository patientRepository, ReadingDeviceRepository readingDeviceRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.deviceRepository = deviceRepository;
        this.patientRepository = patientRepository;
        this.readingDeviceRepository = readingDeviceRepository;
    }


    @Override
    public Optional<ReadingDevice> handle(CreateReadingDeviceCommand command) {
        if (!Objects.equals(authenticatedUserProvider.getCurrentUserType(), "PATIENT"))
            throw new IllegalArgumentException("Only patients can create readings");

        Long userId = authenticatedUserProvider.getCurrentUserId();

        var patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        var device = deviceRepository.findByDeviceId(command.deviceId());

        if (device == null){
            throw new IllegalArgumentException("Device does not exist");
        }

        if (!Objects.equals(device.getPatient().getId(), patient.getId())){
            throw new IllegalArgumentException("Device does not belong to the patient");
        }

        ReadingDevice readingDevice = new ReadingDevice(command, device);

        // 📌 GUARDAR la lectura correctamente
        readingDevice = readingDeviceRepository.save(readingDevice);

        return Optional.of(readingDevice);
    }
}
