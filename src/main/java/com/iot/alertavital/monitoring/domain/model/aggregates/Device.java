package com.iot.alertavital.monitoring.domain.model.aggregates;


import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Device extends AuditableAbstractAggregateRoot<Device> {

    @Column(nullable = false, unique = true)
    private String deviceId;

    @Column(nullable = false)
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Getter
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingDevice> readingDevices = new ArrayList<>();

    public Device() {}

    public Device(String deviceID, Patient patient ) {
        this.deviceId = deviceID;
        this.patient = patient;
        this.status = true;

    }



}
