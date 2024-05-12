package com.nsu.sport_events.events_service.model;

import java.security.Timestamp;

import com.nsu.sport_events.coach_service.model.Coach;
import com.nsu.sport_events.playground_service.model.Playground;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "Events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "start_time", columnDefinition = "timestamp")
    private Timestamp startTime;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false, name = "remaining_seats")
    private Integer remainingSeats;

    @ManyToOne
    @JoinColumn(name = "id")
    private Playground playground;

    @ManyToOne
    @JoinColumn(name = "id")
    private Coach coach;

    @Column(nullable = false)
    private Integer cost;
}