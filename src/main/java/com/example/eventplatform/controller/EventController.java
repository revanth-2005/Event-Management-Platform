package com.example.eventplatform.controller;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.Registration;
import com.example.eventplatform.model.User;
import com.example.eventplatform.service.EventService;
import com.example.eventplatform.service.RegistrationService;
import com.example.eventplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("events", eventService.getAllPublicApprovedEvents());
        return "index";
    }

    @GetMapping("/events/create")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        eventService.createEvent(event, user);
        return "redirect:/events/my-events";
    }

    @GetMapping("/events/my-events")
    public String myEvents(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("createdEvents", eventService.getEventsByCreator(user));
        
        List<Registration> registrations = registrationService.getUserRegistrations(user);
        Map<Long, String> qrCodes = new HashMap<>();
        for (Registration reg : registrations) {
            try {
                qrCodes.put(reg.getId(), registrationService.generateQRCode(reg.getRegistrationCode()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("registrations", registrations);
        model.addAttribute("qrCodes", qrCodes);
        
        return "my-events";
    }
    
    @PostMapping("/events/{id}/register")
    public String registerForEvent(@PathVariable Long id, Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            Event event = eventService.getEventById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            registrationService.registerUserForEvent(user, event);
            return "redirect:/events/my-events?success";
        } catch (Exception e) {
            return "redirect:/?error=" + e.getMessage();
        }
    }
    
    @GetMapping("/calendar")
    public String calendarPage() {
        return "calendar";
    }
}
