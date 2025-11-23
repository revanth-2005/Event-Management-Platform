package com.example.eventplatform.service;

import com.example.eventplatform.model.*;
import com.example.eventplatform.repository.RegistrationRepository;
import com.example.eventplatform.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration registerUserForEvent(User user, Event event) throws Exception {
        if (registrationRepository.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("User already registered for this event");
        }
        if (event.getCapacity() > 0 && registrationRepository.countByEvent(event) >= event.getCapacity()) {
            throw new RuntimeException("Event is full");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(RegistrationStatus.CONFIRMED);
        registration.setRegistrationCode(UUID.randomUUID().toString());

        return registrationRepository.save(registration);
    }

    public List<Registration> getUserRegistrations(User user) {
        return registrationRepository.findByUser(user);
    }
    
    public List<Registration> getEventRegistrations(Event event) {
        return registrationRepository.findByEvent(event);
    }

    public String generateQRCode(String registrationCode) throws Exception {
        return QRCodeGenerator.generateQRCodeImage(registrationCode, 200, 200);
    }

    public void checkInUser(String registrationCode) {
        Registration registration = registrationRepository.findByRegistrationCode(registrationCode)
                .orElseThrow(() -> new RuntimeException("Invalid registration code"));

        if (registration.getStatus() == RegistrationStatus.CHECKED_IN) {
            throw new RuntimeException("User already checked in");
        }
        
        if (registration.getStatus() != RegistrationStatus.CONFIRMED) {
            throw new RuntimeException("Registration is not confirmed");
        }

        registration.setStatus(RegistrationStatus.CHECKED_IN);
        registrationRepository.save(registration);
    }
}
