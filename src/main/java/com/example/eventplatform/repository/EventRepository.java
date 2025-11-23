package com.example.eventplatform.repository;

import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.EventStatus;
import com.example.eventplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatusAndIsPublicTrue(EventStatus status);
    List<Event> findByCreator(User creator);
    List<Event> findByStatus(EventStatus status);
}
