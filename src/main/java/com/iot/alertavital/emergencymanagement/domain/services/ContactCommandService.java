package com.iot.alertavital.emergencymanagement.domain.services;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import com.iot.alertavital.emergencymanagement.domain.model.commands.CreateContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.commands.DeleteContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.commands.UpdateContactCommand;

import java.util.Optional;

public interface ContactCommandService {
    Optional<Contact> handle(CreateContactCommand command);
    Optional<Contact> handle(UpdateContactCommand command);
    void handle(DeleteContactCommand command);
}
