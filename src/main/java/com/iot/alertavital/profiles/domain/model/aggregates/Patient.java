package com.iot.alertavital.profiles.domain.model.aggregates;


import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.healthtracking.domain.model.entities.Disturbances;
import com.iot.alertavital.profiles.domain.model.commands.CreateProfileCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.Birthday;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
public class Patient extends AuditableAbstractAggregateRoot<Patient> {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "birthday", column = @Column(name = "birthday"))})
    private Birthday birthday;


    @Getter
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disturbances> disturbances = new ArrayList<>();


    public Patient() {
    }

    // Constructor actualizado para usar el comando unificado CreateProfileCommand
    public Patient(CreateProfileCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("CreateProfileCommand no puede ser nulo");
        }
        if (command.userType() != UserType.PATIENT) {
            throw new IllegalArgumentException("CreateProfileCommand debe tener userType=PATIENT para crear un Patient");
        }
        this.birthday = new Birthday(command.birthday());
        this.user = command.user();
    }

    public Patient(User user, Birthday birthday) {
        this.birthday = birthday;
        this.user = user;
    }

    public void updateBirthday(LocalDate birthday) {
        this.birthday = new Birthday(birthday);
    }

    public void addDisturbance(Disturbances disturbance) {
        disturbances.add(disturbance);
        disturbance.setPatient(this);
    }

    public void removeDisturbance(Disturbances disturbance) {
        disturbances.remove(disturbance);
        disturbance.setPatient(null);
    }


}
