package com.iot.alertavital.iam.interfaces.rest.transform;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.interfaces.rest.resources.UserProfileResource;
import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;

import java.time.LocalDate;

/**
 * Assembler para transformar User a UserProfileResource
 * Maneja tanto Patient como Caregiver según el tipo de usuario
 */
public class UserProfileResourceAssembler {

    public static UserProfileResource toResourceFromEntity(User user, Patient patient, Caregiver caregiver) {
        String phoneNumber = null;
        LocalDate birthday = null;

        // Obtener birthday de Patient
        if (patient != null && patient.getBirthday() != null) {
            birthday = patient.getBirthday().birthday();
        }
        
        // Obtener phoneNumber de Caregiver
        if (caregiver != null && caregiver.getPhoneNumber() != null) {
            phoneNumber = caregiver.getPhoneNumber().number();
        }

        return new UserProfileResource(
            user.getId(),
            user.getUsername(),
            user.getName().firstName(),
            user.getName().lastName(),
            user.getEmail().address(),
            user.getGender().name(),
            phoneNumber,
            birthday,
            user.getTypeOfUser().name()
        );
    }
}
