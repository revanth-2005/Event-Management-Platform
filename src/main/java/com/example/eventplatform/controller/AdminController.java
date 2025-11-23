package com.example.eventplatform.controller;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.EventStatus;
import com.example.eventplatform.service.EventService;
import com.example.eventplatform.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingEvents", eventService.getPendingEvents());
        model.addAttribute("allEvents", eventService.getAllEvents());
        return "admin-dashboard";
    }

    @PostMapping("/events/{id}/approve")
    public String approveEvent(@PathVariable Long id) {
        eventService.updateEventStatus(id, EventStatus.APPROVED);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/events/{id}/reject")
    public String rejectEvent(@PathVariable Long id) {
        eventService.updateEventStatus(id, EventStatus.REJECTED);
        return "redirect:/admin/dashboard";
    }
    
    // API for Check-in (can be called via AJAX or a simple form)
    @PostMapping("/checkin")
    @ResponseBody
    public ResponseEntity<String> checkIn(@RequestParam String registrationCode) {
        try {
            registrationService.checkInUser(registrationCode);
            return ResponseEntity.ok("Check-in successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
