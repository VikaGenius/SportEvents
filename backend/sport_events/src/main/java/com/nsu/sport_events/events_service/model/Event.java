package com.nsu.sport_events.events_service.model;

import java.sql.Timestamp;

import com.nsu.sport_events.coach_service.model.Coach;
import com.nsu.sport_events.playground_service.model.Playground;

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

    @Column(nullable = false, name = "max_sets")
    private Integer maxSets;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Playground playground;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Coach coach;

    @Column(nullable = false)
    private Integer cost;
}