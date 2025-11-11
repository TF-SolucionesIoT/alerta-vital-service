package com.iot.alertavital.iam.interfaces.rest.resources;

import java.time.LocalDate;

public record CaregiverRegisterRequestResource(String firstName, String lastName, String email, String gender, String username, String password, String phoneNumber) {
}