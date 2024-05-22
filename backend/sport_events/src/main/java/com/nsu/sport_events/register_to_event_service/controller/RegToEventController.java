package com.nsu.sport_events.register_to_event_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nsu.sport_events.register_to_event_service.model.RegistrationToEvent;
import com.nsu.sport_events.register_to_event_service.service.RegToEventService;
import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.register_to_event_service.DTO.RegistrationToEventDTO;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@CrossOrigin(origins = "*")
public class RegToEventController {

    @Autowired
    private RegToEventService registrationService;

    @PostMapping
    public ResponseEntity<Void> registerToEvent(@RequestBody RegistrationToEventDTO registrationDTO) {
        registrationService.addApplication(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> registerToEventByAdmin(@RequestBody RegistrationToEventDTO registrationDTO) {
        registrationService.registerOnEvent(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // пользователи, зареганные на мероприятие
    @GetMapping("/event/{eventId}/users")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable Long eventId) {
        List<User> users = registrationService.getUsersByEvent(eventId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();  // Возвращает статус 204 No Content, если список пуст
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // мероприятия, на которые зарегистрирован пользователь
    @GetMapping("/user/{userId}/events")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable Long userId) {
        List<Event> events = registrationService.getEventsByUser(userId);
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // неподтвержденные заявки на мероприятия
    @GetMapping("/unconfirmed")
    public ResponseEntity<List<RegistrationToEvent>> getUnconfirmedApplications() {
        List<RegistrationToEvent> applications = registrationService.getApplicationsToEvent();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();  
        }
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/confirmed")
    public ResponseEntity<List<RegistrationToEvent>> getConfirmedApplications() {
        List<RegistrationToEvent> registrations = registrationService.getRegistrationsToEvent();
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();  
        }
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @PutMapping("/confirm/{registrationId}")
    public ResponseEntity<?> confirmRegistration(@PathVariable Long registrationId) {
        registrationService.recordConfirmation(registrationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteUserEvent(@PathVariable Long id) {
        registrationService.deleteApplication(id); // отклонение заявки на мероприятие
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{eventId}/{userId}") 
    public ResponseEntity<?> deleteRecord(@PathVariable Long eventId, @PathVariable Long userId) {
        registrationService.deleteRecord(eventId, userId); // удаление записи на мероприятие
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
