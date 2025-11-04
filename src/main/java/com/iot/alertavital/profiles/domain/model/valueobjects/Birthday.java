package com.iot.alertavital.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record Birthday(LocalDate birthday) {

}
