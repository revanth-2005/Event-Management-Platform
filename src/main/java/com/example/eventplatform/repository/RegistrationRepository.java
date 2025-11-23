package com.example.eventplatform.repository;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.Registration;
import com.example.eventplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser(User user);
    List<Registration> findByEvent(Event event);
    Optional<Registration> findByRegistrationCode(String registrationCode);
    long countByEvent(Event event);
    boolean existsByUserAndEvent(User user, Event event);
}
