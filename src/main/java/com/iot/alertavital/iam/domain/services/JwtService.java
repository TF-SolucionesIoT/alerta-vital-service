package com.iot.alertavital.iam.domain.services;

import com.iot.alertavital.iam.domain.model.aggregates.User;

import javax.crypto.SecretKey;

public interface JwtService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean isTokenValid(String token);
    String extractUserId(String token);
    SecretKey getKey();

}