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
import com.nsu.sport_events.smtp_service.service.EmailService;

import java.util.List;

@Service
public class RegToEventService {
    
    @Autowired
    RegToEventRepo registrationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EmailService emailService;

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

        User user = userRepository.findById(dto.getUser()).get();
        Event event = eventRepository.findById(dto.getEvent()).get();

        application.setUser(user);
        application.setEvent(event);

        registrationRepository.save(application);
        
        String to = user.getEmail();
        String subject = "Обработка заявки";
        String text = "Доброго времени суток! Ваша заявка на мероприятие " + event.getName() + " передана на рассмотрение администраторам";

        emailService.sendSimpleMessage(to, subject, text);

        //TODO: добавить функционал с отправлением сообщения на почту о том, 
        //что заявка на мероприятие отправлена
    }

    public void recordConfirmation(Long id) {
        RegistrationToEvent record = registrationRepository.findById(id).get();

        if (record != null) {
            Event event = record.getEvent();
            User user = record.getUser();

            Integer remainingSeats = event.getRemainingSeats();
            if (remainingSeats == 0) {
                String to = user.getEmail();
                String subject = "Регистрация не прошла";
                String text = "Доброго времени суток! К сожалению, места на мероприятие " + event.getName() + " закончились :(";
                emailService.sendSimpleMessage(to, subject, text);

                registrationRepository.deleteById(id);
                return;
            }

            record.setConfirmed(true);
            registrationRepository.save(record);
            event.setRemainingSeats(remainingSeats - 1);
            eventRepository.save(event);

            
            String to = user.getEmail();
            String subject = "Успешная регистрация";
            String text = "Доброго времени суток! Вы зарегистрированы на мероприятие " + event.getName() + " :)";
            emailService.sendSimpleMessage(to, subject, text);
        }
    }

    public void deleteApplication(Long id) {
        registrationRepository.deleteById(id);
    }

    public void deleteRecord(Long eventId, Long userId) {
        registrationRepository.deleteByEventIdAndUserId(eventId, userId);
    }

}
