package com.iot.alertavital.profiles.domain.model.aggregates;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
public class Caregiver extends AuditableAbstractAggregateRoot<Caregiver> {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "phone_number"))})
    private PhoneNumber phoneNumber;

    public Caregiver() {
    }

    public Caregiver(User user, String phoneNumber) {
        this.user = user;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    // Constructor actualizado para usar el comando unificado CreateProfileCommand
    public Caregiver(CreateProfileCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("CreateProfileCommand no puede ser nulo");
        }
        if (command.userType() != UserType.CAREGIVER) {
            throw new IllegalArgumentException("CreateProfileCommand debe tener userType=CAREGIVER para crear un Caregiver");
        }
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.user = command.user();
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }



}
