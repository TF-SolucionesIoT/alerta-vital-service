package com.iot.alertavital.monitoring.domain.model.entities;

import com.iot.alertavital.monitoring.domain.model.commands.CreateAlertCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceId;
    private Instant timestamp;

    public Alert(String deviceId, Instant timestamp) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public Alert(){}

    public Alert(CreateAlertCommand command) {
        this.deviceId = command.deviceId();
        this.timestamp = command.dateTime();
    }


}
