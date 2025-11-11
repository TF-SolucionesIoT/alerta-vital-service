package com.iot.alertavital.monitoring.application.internal;


import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.commands.CreateDeviceCommand;
import com.iot.alertavital.monitoring.domain.services.DeviceCommandService;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    DeviceRepository deviceRepository;
    PatientRepository patientRepository;
    AuthenticatedUserProvider authenticatedUserProvider;

    public DeviceCommandServiceImpl(PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider, DeviceRepository deviceRepository) {
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.deviceRepository = deviceRepository;
    }


    @Override
    public Optional<Device> handle(CreateDeviceCommand command) {
        if (deviceRepository.existsByDeviceId(command.deviceID())){
            throw new IllegalArgumentException("Device already exists");
        }

        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByUser_Id(userId).orElseThrow(()-> new IllegalArgumentException("Patient does not exist"));
        Device device = new Device(command.deviceID(), patient);
        try {
            deviceRepository.save(device);
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

        return Optional.of(device);
    }
}
