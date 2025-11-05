package com.iot.alertavital.profiles.domain.model.aggregates;


import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.healthtracking.domain.model.entities.Disturbances;
import com.iot.alertavital.profiles.domain.model.commands.CreatePatientCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.Birthday;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Patient(CreatePatientCommand command) {
        this.birthday = new Birthday(command.date());
        this.user = command.user();
    }
    public Patient(User user, Birthday birthday) {
        this.birthday = birthday;
        this.user = user;
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
