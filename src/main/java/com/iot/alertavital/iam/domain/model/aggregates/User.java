package com.iot.alertavital.iam.domain.model.aggregates;

import com.iot.alertavital.iam.domain.model.commands.SignUpCaregiverCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpPatientCommand;
import com.iot.alertavital.iam.domain.model.valueobjects.EmailAddress;
import com.iot.alertavital.iam.domain.model.valueobjects.Gender;
import com.iot.alertavital.iam.domain.model.valueobjects.PersonName;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.profiles.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.profiles.domain.model.valueobjects.Birthday;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> implements UserDetails {
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 128)
    private String password;

    @Embedded
    private PersonName name;

    @Embedded
    //Aquí se está indicando que el atributo "address" de la clase EmailAddress debe ser mapeado
    // a una columna llamada "email" en la tabla de la entidad principal.
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    @Column(unique = true)
    private EmailAddress email;

    private Boolean is_active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    // Campos específicos de Caregiver (opcional)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "phone_number"))})
    private PhoneNumber phoneNumber;

    // Campos específicos de Patient (opcional)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "birthday", column = @Column(name = "birthday"))})
    private Birthday birthday;

    public User(){}


    public User(String username, String password, PersonName name, EmailAddress email, String gender, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = Gender.valueOf(gender);
        this.userType = userType;
        this.is_active = true;
    }

    public User(String username, String password, String name, String lastName, String email, String gender, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = new PersonName(name, lastName);
        this.email = new EmailAddress(email);
        this.gender = Gender.valueOf(gender);
        this.userType = userType;
        this.is_active = true;
    }

    public String fullName() { return name.FullName();}
    public String email() { return email.address();}

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInformation(String firstName, String lastName, String email, String username) {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.username = username;
    }

    // Métodos específicos para Caregiver
    public void updatePhoneNumber(String phoneNumber) {
        if (this.userType == UserType.CAREGIVER) {
            this.phoneNumber = new PhoneNumber(phoneNumber);
        } else {
            throw new IllegalArgumentException("Solo los cuidadores pueden tener número de teléfono");
        }
    }

    public String getPhoneNumber() {
        return this.phoneNumber != null ? this.phoneNumber.number() : null;
    }

    // Métodos específicos para Patient
    public void updateBirthday(LocalDate birthday) {
        if (this.userType == UserType.PATIENT) {
            this.birthday = new Birthday(birthday);
        } else {
            throw new IllegalArgumentException("Solo los pacientes pueden tener fecha de nacimiento");
        }
    }

    public LocalDate getBirthday() {
        return this.birthday != null ? this.birthday.birthday() : null;
    }

    // Getters adicionales
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isCaregiver() {
        return this.userType == UserType.CAREGIVER;
    }

    public boolean isPatient() {
        return this.userType == UserType.PATIENT;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    public User(SignUpPatientCommand command, String encryptedPassword){
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.gender = Gender.valueOf(command.gender().toUpperCase());
        this.username = command.username();
        this.password = encryptedPassword;
        this.userType = UserType.PATIENT;
        this.is_active = true;
        // Inicializar campos específicos de paciente si están en el comando
        if (command.birthday() != null) {
            this.birthday = new Birthday(command.birthday());
        }
    }

    public User(SignUpCaregiverCommand command, String encryptedPassword){
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.gender = Gender.valueOf(command.gender().toUpperCase());
        this.username = command.username();
        this.password = encryptedPassword;
        this.userType = UserType.CAREGIVER;
        this.is_active = true;
        // Inicializar campos específicos de cuidador si están en el comando
        if (command.phoneNumber() != null) {
            this.phoneNumber = new PhoneNumber(command.phoneNumber());
        }
    }

    // Getter para ID (requerido por JPA y usado en servicios)
    public Long getId() {
        return super.getId();
    }
}
