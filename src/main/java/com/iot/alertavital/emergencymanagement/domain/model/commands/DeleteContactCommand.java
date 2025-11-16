package com.iot.alertavital.emergencymanagement.domain.model.commands;

public record DeleteContactCommand(Long id) {

    public DeleteContactCommand {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or lower than one");
        }
    }
}
