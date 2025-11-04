package com.iot.alertavital.iam.interfaces.rest.resources;

public record RegisterRequestResource(String firstName, String lastName, String email, String gender, String username, String password) {
}