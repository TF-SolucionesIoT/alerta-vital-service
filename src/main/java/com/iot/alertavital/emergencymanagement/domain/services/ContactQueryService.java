package com.iot.alertavital.emergencymanagement.domain.services;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import com.iot.alertavital.emergencymanagement.domain.model.queries.GetAllContactsByPatientIdQuery;

import java.util.List;

public interface ContactQueryService {
    List<Contact> handle(GetAllContactsByPatientIdQuery query);
}
