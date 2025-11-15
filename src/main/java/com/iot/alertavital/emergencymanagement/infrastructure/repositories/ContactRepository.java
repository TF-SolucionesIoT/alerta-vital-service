package com.iot.alertavital.emergencymanagement.infrastructure.repositories;

import com.iot.alertavital.emergencymanagement.domain.model.aggregates.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByPatient_Id(Long patientId);

    Optional<Contact> findByIdAndPatient_User_Id(Long id, Long userId);

    boolean existsByPhoneNumber_PhoneNumber(String phoneNumber);
}
