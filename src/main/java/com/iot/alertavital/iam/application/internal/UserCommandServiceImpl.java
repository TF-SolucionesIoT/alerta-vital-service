package com.iot.alertavital.iam.application.internal;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.model.commands.UpdateInformationCommand;
import com.iot.alertavital.iam.domain.model.commands.UpdatePasswordCommand;
import com.iot.alertavital.iam.domain.services.UserCommandService;
import com.iot.alertavital.iam.infrastructure.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCommandServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Optional<User> handle(UpdatePasswordCommand command) {
        Optional<User> userOptional = userRepository.findById(command.userId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOptional.get();

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(command.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // Encriptar y actualizar la nueva contraseña
        String encryptedPassword = passwordEncoder.encode(command.newPassword());
        user.updatePassword(encryptedPassword);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar la contraseña: " + e.getMessage());
        }

        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<User> handle(UpdateInformationCommand command) {
        Optional<User> userOptional = userRepository.findById(command.userId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOptional.get();

        // Verificar si el nuevo username ya existe (si es diferente al actual)
        if (!user.getUsername().equals(command.username())) {
            Optional<User> existingUser = userRepository.findByUsername(command.username());
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
        }

        // Verificar si el nuevo email ya existe (si es diferente al actual)
        if (!user.email().equals(command.email())) {
            Optional<User> existingUserByEmail = userRepository.findByEmail_Address(command.email());
            if (existingUserByEmail.isPresent()) {
                throw new IllegalArgumentException("El email ya existe");
            }
        }

        // Actualizar la información
        user.updateInformation(command.firstName(), command.lastName(), command.email(), command.username());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar la información: " + e.getMessage());
        }

        return Optional.of(user);
    }
}

