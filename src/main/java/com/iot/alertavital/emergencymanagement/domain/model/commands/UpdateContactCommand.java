package com.iot.alertavital.emergencymanagement.domain.model.commands;

public record UpdateContactCommand(Long id,
                                   String name,
                                   String connection,
                                   String phoneNumber) {

    public UpdateContactCommand {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or lower than one");
        }
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(connection == null || connection.isEmpty()) {
            throw new IllegalArgumentException("Connection cannot be null or empty");
        }
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
    }
}
