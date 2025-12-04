package com.iot.alertavital.monitoring.application.internal;


import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.commands.CreateAlertCommand;
import com.iot.alertavital.monitoring.domain.model.commands.CreateDeviceCommand;
import com.iot.alertavital.monitoring.domain.model.entities.Alert;
import com.iot.alertavital.monitoring.domain.services.DeviceCommandService;
import com.iot.alertavital.monitoring.infrastructure.repositories.AlertRepository;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    final DeviceRepository deviceRepository;
    final PatientRepository patientRepository;
    final AuthenticatedUserProvider authenticatedUserProvider;
    final AlertRepository alertRepository;

    public DeviceCommandServiceImpl(PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider, DeviceRepository deviceRepository, AlertRepository alertRepository) {
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.deviceRepository = deviceRepository;
        this.alertRepository = alertRepository;
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

    @Override
    public Optional<?> handle(CreateAlertCommand command) {
        // Implementation for handling CreateAlertCommand
        if (!Objects.equals(authenticatedUserProvider.getCurrentUserType(), "PATIENT"))
            throw new IllegalArgumentException("Only patients can create alerts");

        if (!deviceRepository.existsByDeviceId(command.deviceId())){
            throw new IllegalArgumentException("Device does not exist");
        }

        //PATIENT CAN ONLY CREATE ALERTS FOR THEIR OWN DEVICES
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByUser_Id(userId).orElseThrow(()-> new
                IllegalArgumentException("Patient does not exist"));

        var device = deviceRepository.findByDeviceId(command.deviceId());

        if (!Objects.equals(device.getPatient().getId(), patient.getId())){
            throw new IllegalArgumentException("Patient can only create alerts for their own devices");
        }
        //END OF CHECK

        Alert alert = new Alert(command);

        try {
            alertRepository.save(alert);
            return Optional.of(alert);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
