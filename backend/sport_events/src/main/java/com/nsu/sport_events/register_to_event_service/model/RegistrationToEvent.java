package com.nsu.sport_events.register_to_event_service.model;

import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.events_service.model.Event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Registration_to_event", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "event_id"})
})
public class RegistrationToEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ue_id")
    private Long ueId;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id") 
    private Event event;

    @Builder.Default
    private boolean confirmed = false; // для функционала подтверждения администратором
}
