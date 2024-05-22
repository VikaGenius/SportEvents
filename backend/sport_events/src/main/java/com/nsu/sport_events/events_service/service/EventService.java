package com.nsu.sport_events.events_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.sql.Date;

import com.nsu.sport_events.coach_service.repository.CoachRepository;
import com.nsu.sport_events.events_service.DTO.EventDTO;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.events_service.repository.EventRepository;
import com.nsu.sport_events.playground_service.repository.PlaygroundRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private PlaygroundRepository playgroundRepository;

    public void addEvent(EventDTO eventDTO) {
        Event event = new Event();

        event.setEId(eventDTO.getEId());
        event.setName(eventDTO.getName());
        event.setRemainingSeats(eventDTO.getRemainingSeats());
        event.setMaxSets(eventDTO.getMaxSeats());
        event.setCoach(coachRepository.findById(eventDTO.getCoach()).get());
        event.setDuration(eventDTO.getDuration());
        event.setCost(eventDTO.getCost());
        event.setPlayground(playgroundRepository.findById(eventDTO.getPlayground()).get());
        event.setStartTime(eventDTO.getStartTime());

        eventRepository.save(event);
    }

    public Page<Event> getAllEvents(Map<String, String> filters, Pageable pageable) {
        Specification<Event> spec = createSpecification(filters);
        return eventRepository.findAll(spec, pageable);
    }

    private Specification<Event> createSpecification(Map<String, String> filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.containsKey("name") && !filters.get("name").isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filters.get("name").toLowerCase() + "%"));
            }
            if (filters.containsKey("date") && !filters.get("date").isEmpty()) {
                Date date = Date.valueOf(filters.get("date")); // Преобразование строки в java.sql.Date
                predicates.add(cb.equal(cb.function("DATE", Date.class, root.get("startTime")), date));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Transactional()
    public List<Event> getAllEvents(Map<String, String> filters) {
        Specification<Event> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.containsKey("name") && !filters.get("name").isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filters.get("name").toLowerCase() + "%"));
            }
            if (filters.containsKey("date") && !filters.get("date").isEmpty()) {
                Date date = Date.valueOf(filters.get("date")); // Преобразование строки в java.sql.Date
                predicates.add(cb.equal(cb.function("DATE", Date.class, root.get("startTime")), date));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return eventRepository.findAll(spec);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).get();
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    public void updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) return;

        event.setEId(eventDTO.getEId());
        event.setName(eventDTO.getName());
        event.setRemainingSeats(eventDTO.getRemainingSeats());
        event.setMaxSets(eventDTO.getMaxSeats());
        event.setCoach(coachRepository.findById(eventDTO.getCoach()).get());
        event.setDuration(eventDTO.getDuration());
        event.setCost(eventDTO.getCost());
        event.setPlayground(playgroundRepository.findById(eventDTO.getPlayground()).get());
        event.setStartTime(eventDTO.getStartTime());

        eventRepository.save(event);
    }



}
