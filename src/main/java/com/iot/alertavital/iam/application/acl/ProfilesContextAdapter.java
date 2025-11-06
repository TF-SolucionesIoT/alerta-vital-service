package com.iot.alertavital.iam.application.acl;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.interfaces.ACL.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProfilesContextAdapter {

    private final ProfilesContextFacade profilesFacade;

    public ProfilesContextAdapter(ProfilesContextFacade profilesFacade) {
        this.profilesFacade = profilesFacade;
    }

    public void createPatientForUser(User user, LocalDate birthday) {
        var command = new CreatePatientCommand(user, birthday);
        profilesFacade.createPatient(command);
    }

    public void createCaregiverForUser(User user, String phoneNumber) {
        var command = new CreateCaregiverCommand(user, phoneNumber);
        profilesFacade.createCaregiver(command);
    }
}