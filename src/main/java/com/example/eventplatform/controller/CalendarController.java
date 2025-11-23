package com.example.eventplatform.controller;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class CalendarController {

    @Autowired
    private EventService eventService;

    @GetMapping("/calendar-feed")
    public List<Map<String, String>> getCalendarEvents() {
        List<Event> events = eventService.getAllPublicApprovedEvents();
        return events.stream().map(event -> {
            return Map.of(
                    "title", event.getTitle(),
                    "start", event.getDateTimeStart().toString(),
                    "end", event.getDateTimeEnd().toString(),
                    "url", "/events/" + event.getId() // Optional: link to details
            );
        }).collect(Collectors.toList());
    }
}
