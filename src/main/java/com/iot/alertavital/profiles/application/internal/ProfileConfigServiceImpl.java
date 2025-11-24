package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.infrastructure.repositories.UserRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.domain.model.commands.ChangePasswordCommand;
import com.iot.alertavital.profiles.domain.services.ProfileConfigService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class ProfileConfigServiceImpl implements ProfileConfigService {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    public ProfileConfigServiceImpl(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider, BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<User> handle(ChangePasswordCommand command) {
        var userId = authenticatedUserProvider.getCurrentUserId();
        var user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new IllegalStateException("Old password is incorrect");
        }

        String hashedPassword = passwordEncoder.encode(command.newPassword());
        user.setPassword(hashedPassword);

        try {
            var updatedUser = userRepository.save(user);
            return Optional.of(updatedUser);
        } catch (Exception e) {
            throw new IllegalStateException("Could not save user", e);
        }

    }
}
