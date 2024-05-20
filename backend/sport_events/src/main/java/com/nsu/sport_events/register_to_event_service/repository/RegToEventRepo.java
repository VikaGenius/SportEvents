package com.nsu.sport_events.register_to_event_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.events_service.model.Event;
import com.nsu.sport_events.register_to_event_service.model.RegistrationToEvent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface RegToEventRepo extends JpaRepository<RegistrationToEvent, Long> {

    // Найти все мероприятия, на которые зарегистрирован пользователь
    @Query("SELECT r.event FROM RegistrationToEvent r WHERE r.user.id = :userId")
    List<Event> findEventsByUserId(Long userId);

    // Найти всех пользователей, зарегистрированных на мероприятие
    @Query("SELECT r.user FROM RegistrationToEvent r WHERE r.event.id = :eventId AND r.confirmed = true")
    List<User> findUsersByEventId(Long eventId);

    // Найти все неподтвержденные заявки на мероприятия
    @Query("SELECT r FROM RegistrationToEvent r WHERE r.confirmed = false")
    List<RegistrationToEvent> findApplicationsToEvents();

    @Query("SELECT r FROM RegistrationToEvent r WHERE r.confirmed = true")
    List<RegistrationToEvent> findRegistrationsToEvents();

    @Transactional
    @Modifying
    @Query("DELETE FROM RegistrationToEvent r WHERE r.event.id = :eventId AND r.user.id = :userId")
    void deleteByEventIdAndUserId(Long eventId, Long userId);
}
