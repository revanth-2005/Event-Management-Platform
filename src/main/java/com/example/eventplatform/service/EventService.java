package com.example.eventplatform.service;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.EventStatus;
import com.example.eventplatform.model.User;
import com.example.eventplatform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event, User creator) {
        event.setCreator(creator);
        if (event.isRequiresAdminApproval()) {
            event.setStatus(EventStatus.PENDING_APPROVAL);
        } else {
            event.setStatus(EventStatus.APPROVED);
        }
        return eventRepository.save(event);
    }

    public List<Event> getAllPublicApprovedEvents() {
        return eventRepository.findByStatusAndIsPublicTrue(EventStatus.APPROVED);
    }

    public List<Event> getEventsByCreator(User creator) {
        return eventRepository.findByCreator(creator);
    }

    public List<Event> getPendingEvents() {
        return eventRepository.findByStatus(EventStatus.PENDING_APPROVAL);
    }
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void updateEventStatus(Long eventId, EventStatus status) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(status);
        eventRepository.save(event);
    }
}
