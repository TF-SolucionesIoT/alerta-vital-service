package com.iot.alertavital.monitoring.domain.model.entities;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
public class ReadingDevice extends AuditableModel {

    @NotNull
    private Integer spO2;

    @NotNull
    private Integer pulse;

    @NotNull
    private Instant timestamp;


    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public ReadingDevice() {}

    public ReadingDevice(Integer spO2, Integer pulse, Instant timestamp, Device device) {
        this.spO2 = spO2;
        this.pulse = pulse;
        this.timestamp = timestamp;
        this.device = device;
    }


}
