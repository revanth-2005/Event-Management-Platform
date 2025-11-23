package com.example.eventplatform.config;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.EventStatus;
import com.example.eventplatform.model.Role;
import com.example.eventplatform.model.User;
import com.example.eventplatform.repository.EventRepository;
import com.example.eventplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(Role.ROLE_ADMIN);
            adminRoles.add(Role.ROLE_USER);
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            // Create User
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@example.com");
            user.setFirstName("Regular");
            user.setLastName("User");
            user.setRoles(Collections.singleton(Role.ROLE_USER));
            userRepository.save(user);

            // Create Events
            Event event1 = new Event();
            event1.setTitle("Spring Boot Workshop");
            event1.setDescription("Learn Spring Boot 3.x");
            event1.setLocation("Tech Hub");
            event1.setDateTimeStart(LocalDateTime.now().plusDays(2));
            event1.setDateTimeEnd(LocalDateTime.now().plusDays(2).plusHours(2));
            event1.setCapacity(50);
            event1.setPublic(true);
            event1.setRequiresAdminApproval(false);
            event1.setStatus(EventStatus.APPROVED);
            event1.setCreator(admin);
            eventRepository.save(event1);

            Event event2 = new Event();
            event2.setTitle("Private Team Meeting");
            event2.setDescription("Internal discussion");
            event2.setLocation("Office Room 101");
            event2.setDateTimeStart(LocalDateTime.now().plusDays(5));
            event2.setDateTimeEnd(LocalDateTime.now().plusDays(5).plusHours(1));
            event2.setCapacity(10);
            event2.setPublic(false);
            event2.setRequiresAdminApproval(false);
            event2.setStatus(EventStatus.APPROVED);
            event2.setCreator(user);
            eventRepository.save(event2);

            Event event3 = new Event();
            event3.setTitle("Community Gala");
            event3.setDescription("A big event needing approval");
            event3.setLocation("City Hall");
            event3.setDateTimeStart(LocalDateTime.now().plusDays(10));
            event3.setDateTimeEnd(LocalDateTime.now().plusDays(10).plusHours(4));
            event3.setCapacity(200);
            event3.setPublic(true);
            event3.setRequiresAdminApproval(true);
            event3.setStatus(EventStatus.PENDING_APPROVAL);
            event3.setCreator(user);
            eventRepository.save(event3);
        }
    }
}
