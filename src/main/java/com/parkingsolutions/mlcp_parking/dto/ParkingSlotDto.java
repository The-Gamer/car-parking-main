package com.parkingsolutions.mlcp_parking.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingSlotDto {
  private String parkingId;
  private String carNumber;
  private LocalDateTime checkInDateTime;
  private LocalDateTime expiryDateTime;
}
