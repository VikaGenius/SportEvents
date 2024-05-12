package com.nsu.sport_events.events_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsu.sport_events.events_service.model.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);

    void deleteById(Long id);
    
    List<Event> findAll();



}
