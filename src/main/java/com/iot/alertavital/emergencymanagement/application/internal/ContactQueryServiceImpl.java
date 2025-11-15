package com.iot.alertavital.emergencymanagement.application.internal;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import com.iot.alertavital.emergencymanagement.domain.model.queries.GetAllContactsByPatientIdQuery;
import com.iot.alertavital.emergencymanagement.domain.services.ContactQueryService;
import com.iot.alertavital.emergencymanagement.infrastructure.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactQueryServiceImpl implements ContactQueryService {

    private final ContactRepository contactRepository;

    public ContactQueryServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> handle(GetAllContactsByPatientIdQuery query) {
        return contactRepository.findAllByPatient_Id(query.patientId());
    }
}
