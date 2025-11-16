package com.iot.alertavital.profiles.domain.model.aggregates;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.commands.CreateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    public Caregiver(CreateCaregiverCommand command) {
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.user = command.user();
    }




}
