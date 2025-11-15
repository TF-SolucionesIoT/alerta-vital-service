package com.iot.alertavital.emergencymanagement.application.internal;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import com.iot.alertavital.emergencymanagement.domain.model.commands.CreateContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.commands.DeleteContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.commands.UpdateContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.emergencymanagement.domain.services.ContactCommandService;
import com.iot.alertavital.emergencymanagement.infrastructure.repositories.ContactRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactCommandServiceImpl implements ContactCommandService {

    private final ContactRepository contactRepository;
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public ContactCommandServiceImpl(ContactRepository contactRepository,
                                     PatientRepository patientRepository,
                                     AuthenticatedUserProvider authenticatedUserProvider) {
        this.contactRepository = contactRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Optional<Contact> handle(CreateContactCommand command) {
        if(contactRepository.existsByPhoneNumber_PhoneNumber(command.phoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        Long userId = authenticatedUserProvider.getCurrentUserId();

        var patient = patientRepository.findByUser_Id(userId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));

        Contact contact = new Contact(command.name(),
                command.connection(),
                new PhoneNumber(command.phoneNumber()),
                patient);

        try {
            contactRepository.save(contact);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Contact could not be saved: " + e.getMessage());
        }

        return Optional.of(contact);
    }

    @Override
    public Optional<Contact> handle(UpdateContactCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var contact = contactRepository.findByIdAndPatient_User_Id(command.id(), userId).orElseThrow(() -> new IllegalArgumentException("Contact does not exist"));

        contact.setName(command.name());
        contact.setConnection(command.connection());
        contact.setPhoneNumber(new PhoneNumber(command.phoneNumber()));

        try {
            var updatedContact = contactRepository.save(contact);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Contact could not be updated: " + e.getMessage());
        }

        return Optional.of(contact);
    }

    @Override
    public void handle(DeleteContactCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var contact = contactRepository.findByIdAndPatient_User_Id(command.id(), userId).orElseThrow(() -> new IllegalArgumentException("Contact does not exist"));

        try {
            contactRepository.delete(contact);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Contact could not be deleted: " + e.getMessage());
        }
    }
}
