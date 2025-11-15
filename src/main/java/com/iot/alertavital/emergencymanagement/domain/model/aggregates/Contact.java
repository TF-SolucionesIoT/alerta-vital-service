package com.iot.alertavital.emergencymanagement.domain.model.aggregates;

import com.iot.alertavital.emergencymanagement.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Contact extends AuditableAbstractAggregateRoot<Contact> {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String connection;

    @Embedded
    @NotBlank
    @Column(unique = true)
    private PhoneNumber phoneNumber;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Contact() {}

    public Contact(String name, String connection, PhoneNumber phoneNumber, Patient patient) {
        this.name = name;
        this.connection = connection;
        this.phoneNumber = phoneNumber;
        this.patient = patient;
    }

}
