package com.iot.alertavital.iam.application.internal;

import com.iot.alertavital.iam.application.external.results.AuthResponseResult;
import com.iot.alertavital.iam.application.external.results.RegisterResponseResult;
import com.iot.alertavital.iam.application.internal.services.AuthService;
import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.model.commands.SignInCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpCommand;
import com.iot.alertavital.iam.domain.services.JwtService;
import com.iot.alertavital.iam.infrastructure.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtService jwtService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseResult login(SignInCommand command) {
        User user = userRepository.findByUsername(command.username()).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String accessToken = jwtService.generateAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(user.getId()));

        return new AuthResponseResult(accessToken, refreshToken);
    }

    @Override
    public RegisterResponseResult register(SignUpCommand command) {
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new IllegalArgumentException("el nombre de usuario ya esta en uso");
        }

        String hashedPassword = passwordEncoder.encode(command.password());

        var user = new User(command, hashedPassword);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("un error ha ocurrido mientras se guarda el usuario" + e.getMessage());
        }

        String accessToken = jwtService.generateAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(user.getId()));

        return new RegisterResponseResult(accessToken, refreshToken, user.getUsername());
    }



}