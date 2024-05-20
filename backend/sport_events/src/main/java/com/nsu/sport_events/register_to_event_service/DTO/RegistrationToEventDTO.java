package com.nsu.sport_events.register_to_event_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationToEventDTO {
    private Long ueId;
    private Long user;
    private Long event;
    private boolean confirmed;

}
