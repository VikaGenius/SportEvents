package com.nsu.sport_events.events_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.nsu.sport_events.coach_service.repository.CoachRepository;
import com.nsu.sport_events.events_service.DTO.EventDTO;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.events_service.repository.EventRepository;
import com.nsu.sport_events.playground_service.repository.PlaygroundRepository;

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
