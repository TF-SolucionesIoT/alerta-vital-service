package com.iot.alertavital.profiles.domain.model.aggregates;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.profiles.domain.model.valueobjects.PhoneNumber;
import com.iot.alertavital.profiles.domain.model.valueobjects.Birthday;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Agregado Profile unificado que maneja tanto Patients como Caregivers
 * Este agregado centraliza toda la información específica de perfil
 */
@Entity
@Getter
@Setter
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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

    public Profile() {}

    // Constructor para Caregiver
    public Profile(User user, String phoneNumber) {
        this.user = user;
        this.userType = UserType.CAREGIVER;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    // Constructor para Patient
    public Profile(User user, LocalDate birthday) {
        this.user = user;
        this.userType = UserType.PATIENT;
        this.birthday = new Birthday(birthday);
    }

    // Constructor genérico
    public Profile(User user, UserType userType, String phoneNumber, LocalDate birthday) {
        this.user = user;
        this.userType = userType;

        if (userType == UserType.CAREGIVER && phoneNumber != null) {
            this.phoneNumber = new PhoneNumber(phoneNumber);
        } else if (userType == UserType.PATIENT && birthday != null) {
            this.birthday = new Birthday(birthday);
        }
    }

    // Métodos de negocio
    public void updatePhoneNumber(String phoneNumber) {
        if (this.userType != UserType.CAREGIVER) {
            throw new IllegalArgumentException("Solo los cuidadores pueden tener número de teléfono");
        }
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void updateBirthday(LocalDate birthday) {
        if (this.userType != UserType.PATIENT) {
            throw new IllegalArgumentException("Solo los pacientes pueden tener fecha de nacimiento");
        }
        this.birthday = new Birthday(birthday);
    }

    public void updateProfileInformation(String phoneNumber, LocalDate birthday) {
        if (this.userType == UserType.CAREGIVER && phoneNumber != null) {
            updatePhoneNumber(phoneNumber);
        } else if (this.userType == UserType.PATIENT && birthday != null) {
            updateBirthday(birthday);
        }
    }

    // Getters de conveniencia
    public String getPhoneNumberValue() {
        return phoneNumber != null ? phoneNumber.number() : null;
    }

    public LocalDate getBirthdayValue() {
        return birthday != null ? birthday.birthday() : null;
    }

    public boolean isCaregiver() {
        return userType == UserType.CAREGIVER;
    }

    public boolean isPatient() {
        return userType == UserType.PATIENT;
    }

    public String getFullName() {
        return user.fullName();
    }

    public String getEmail() {
        return user.email();
    }

    public String getUsername() {
        return user.getUsername();
    }

    // Getter para ID (requerido para servicios)
    public Long getId() {
        return super.getId();
    }

    // Getter para User (requerido para controladores)
    public User getUser() {
        return user;
    }
}
