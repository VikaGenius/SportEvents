package com.nsu.sport_events.register_to_event_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.auth_service.repository.UserRepository;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.events_service.repository.EventRepository;
import com.nsu.sport_events.register_to_event_service.DTO.RegistrationToEventDTO;
import com.nsu.sport_events.register_to_event_service.model.RegistrationToEvent;
import com.nsu.sport_events.register_to_event_service.repository.RegToEventRepo;

import java.util.List;

@Service
public class RegToEventService {
    
    @Autowired
    RegToEventRepo registrationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    public List<RegistrationToEvent> getApplicationsToEvent() {
        return registrationRepository.findApplicationsToEvents();
    }

    public List<RegistrationToEvent> getRegistrationsToEvent() {
        return registrationRepository.findRegistrationsToEvents();
    }

    public List<User> getUsersByEvent(Long event_id) {
        return registrationRepository.findUsersByEventId(event_id);
    }

    public List<Event> getEventsByUser(Long user_id) {
        return registrationRepository.findEventsByUserId(user_id);
    }

    public void addApplication(RegistrationToEventDTO dto) {
        RegistrationToEvent application = new RegistrationToEvent();

        application.setUser(userRepository.findById(dto.getUser()).get());
        application.setEvent(eventRepository.findById(dto.getEvent()).get());

        registrationRepository.save(application);

        //TODO: добавить функционал с отправлением сообщения на почту о том, 
        //что заявка на мероприятие отправлена
    }

    public void recordConfirmation(Long id) {
        RegistrationToEvent record = registrationRepository.findById(id).get();

        if (record != null) {
            Event event = record.getEvent();
            Integer remainingSeats = event.getRemainingSeats();
            if (remainingSeats == 0) {
                //отправить сообщение о том, что мест нет

                registrationRepository.deleteById(id);
                return;
            }

            record.setConfirmed(true);
            registrationRepository.save(record);
            event.setRemainingSeats(remainingSeats - 1);
            //отправить сообщение о том, что запись прошла успешно
        }
    }

    public void deleteApplication(Long id) {
        registrationRepository.deleteById(id);
    }

    public void deleteRecord(Long eventId, Long userId) {
        registrationRepository.deleteByEventIdAndUserId(eventId, userId);
    }

}
