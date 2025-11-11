package com.iot.alertavital.iam.application.internal;

import com.iot.alertavital.iam.application.acl.ProfilesContextAdapter;
import com.iot.alertavital.iam.application.external.results.AuthResponseResult;
import com.iot.alertavital.iam.application.external.results.RegisterResponseResult;
import com.iot.alertavital.iam.application.internal.services.AuthService;
import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.model.commands.SignInCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpCaregiverCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpPatientCommand;
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
    private final ProfilesContextAdapter profilesContextAdapter;

    public AuthServiceImpl(JwtService jwtService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProfilesContextAdapter profilesContextAdapter) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profilesContextAdapter = profilesContextAdapter;
    }

    public AuthResponseResult login(SignInCommand command) {
        User user = userRepository.findByUsername(command.username()).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponseResult(accessToken, refreshToken);
    }

    @Override
    public RegisterResponseResult register(SignUpPatientCommand command) {
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

        profilesContextAdapter.createPatientForUser(user, command.birthday());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new RegisterResponseResult(accessToken, refreshToken, user.getUsername());
    }

    @Override
    public RegisterResponseResult register(SignUpCaregiverCommand command) {
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

        profilesContextAdapter.createCaregiverForUser(user, command.phoneNumber());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new RegisterResponseResult(accessToken, refreshToken, user.getUsername());
    }


}