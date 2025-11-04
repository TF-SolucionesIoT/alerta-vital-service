package com.iot.alertavital.profiles.domain.model.aggregates;


import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.valueobjects.Birthday;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

@Entity
public class Patient extends AuditableAbstractAggregateRoot<Patient> {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "birthday", column = @Column(name = "birthday"))})
    private Birthday birthday;

    public Patient() {
    }

    public Patient(Birthday birthday) {
        this.birthday = birthday;
    }


}
