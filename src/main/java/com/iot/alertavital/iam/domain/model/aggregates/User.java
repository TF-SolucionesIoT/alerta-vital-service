package com.iot.alertavital.iam.domain.model.aggregates;

import com.iot.alertavital.iam.domain.model.commands.SignUpCaregiverCommand;
import com.iot.alertavital.iam.domain.model.commands.SignUpPatientCommand;
import com.iot.alertavital.iam.domain.model.valueobjects.EmailAddress;
import com.iot.alertavital.iam.domain.model.valueobjects.Gender;
import com.iot.alertavital.iam.domain.model.valueobjects.PersonName;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;



    public User(){}


    public User(String username, String password, PersonName name, EmailAddress email, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = Gender.valueOf(gender);
        this.is_active = true;
    }

    public User(String username, String password, String name, String lastName, String email, String gender) {
        this.username = username;
        this.password = password;
        this.name = new PersonName(name, lastName);
        this.email = new EmailAddress(email);
        this.gender = Gender.valueOf(gender);
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
        this.is_active = true;
    }

    public User(SignUpCaregiverCommand command, String encryptedPassword){
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.gender = Gender.valueOf(command.gender().toUpperCase());
        this.username = command.username();
        this.password = encryptedPassword;
        this.is_active = true;
    }

}
