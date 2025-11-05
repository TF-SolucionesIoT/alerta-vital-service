package com.iot.alertavital.iam.interfaces.rest.resources;

import java.time.LocalDate;

public record RegisterRequestResource(String firstName, String lastName, String email, String gender, String username, String password, LocalDate birthday) {
}