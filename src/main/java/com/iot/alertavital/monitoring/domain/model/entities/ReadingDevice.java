package com.iot.alertavital.monitoring.domain.model.entities;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.commands.CreateReadingDeviceCommand;
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
    private Integer bpm;

    @NotNull
    private Integer bpDiastolic;

    @NotNull
    private Integer bpSystolic;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public ReadingDevice() {}

    public ReadingDevice(Integer spO2, Integer pulse, Integer bpDiastolic, Integer bpSystolic, Device device) {
        this.spO2 = spO2;
        this.bpm = pulse;
        this.bpDiastolic = bpDiastolic;
        this.bpSystolic = bpSystolic;
        this.device = device;
    }

    public ReadingDevice(CreateReadingDeviceCommand command, Device device) {
        this.spO2 = command.spO2();
        this.bpm = command.bpm();
        this.bpDiastolic = command.bpDiastolic();
        this.bpSystolic = command.bpSystolic();
        this.device = device;
    }


}
