package com.iot.alertavital.iam.application.external.results;

public record RegisterResponseResult(String accessToken, String refreshToken, Long userId, String typeOfUser, String fullName) {
}