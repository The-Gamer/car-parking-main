package com.parkingsolutions.mlcp_parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ParkingSlotDto {
    private String parkingId;
    private String carNumber;
    private LocalDateTime checkInDateTime;
    private LocalDateTime expiryDateTime;
}
