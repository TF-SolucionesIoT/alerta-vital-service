package com.iot.alertavital.emergencymanagement.domain.model.commands;

public record CreateContactCommand(String name,
                                   String connection,
                                   String phoneNumber,
                                   Long patientId) {

    public CreateContactCommand {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(connection == null || connection.isEmpty()) {
            throw new IllegalArgumentException("Connection cannot be null or empty");
        }
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if(patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient ID cannot be null or lower than one");
        }
    }

}
