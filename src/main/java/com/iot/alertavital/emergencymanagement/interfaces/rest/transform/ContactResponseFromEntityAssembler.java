package com.iot.alertavital.emergencymanagement.interfaces.rest.transform;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.ContactResponse;

public class ContactResponseFromEntityAssembler {
    public static ContactResponse toResource(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getConnection(),
                contact.getPhoneNumber().phoneNumber(),
                contact.getPatient().getId()
        );

    }
}
