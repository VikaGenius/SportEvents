package com.nsu.sport_events.events_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsu.sport_events.events_service.DTO.EventDTO;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.events_service.service.EventService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // @GetMapping
    // public ResponseEntity<List<Event>> getAllEvents() {
    //     List<Event> events = eventService.getAllEvents();
    //     return new ResponseEntity<>(events, HttpStatus.OK);
    // }

    // @GetMapping
    // public ResponseEntity<List<Event>> getAllEvents(@RequestParam Map<String, String> filters) {
    //     List<Event> events = eventService.getAllEvents(filters);
    //     return ResponseEntity.ok(events);
    // }

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(@RequestParam Map<String, String> filters, Pageable pageable) {
        Page<Event> events = eventService.getAllEvents(filters, pageable);
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Void> addEvent(@RequestBody EventDTO event) {
        eventService.addEvent(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable("id") Long id, @RequestBody EventDTO updatedEvent) {
        eventService.updateEvent(id, updatedEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

}
