package com.nsu.sport_events.events_service.DTO;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class EventDTO {
    private Long id;
    private String name;
    private Timestamp startTime;
    private String duration;
    private Integer remainingSeats;
    private Long playground;
    private Long coach;
    private Integer cost;
}
