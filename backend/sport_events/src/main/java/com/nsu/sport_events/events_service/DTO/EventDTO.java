package com.nsu.sport_events.events_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private Timestamp startTime;
    private String duration;
    private Integer remainingSeats;
    private Integer maxSets;
    private Long playground;
    private Long coach;
    private Integer cost;
}
