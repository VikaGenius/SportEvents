package com.nsu.sport_events.events_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nsu.sport_events.events_service.model.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @SuppressWarnings("null")
    Optional<Event> findById(Long id);

    @SuppressWarnings("null")
    void deleteById(Long id);
    
    @SuppressWarnings("null")
    List<Event> findAll();

    // List<Event> findAll(Specification spec)


}

