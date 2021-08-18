package com.parkingsolutions.mlcp_parking.entity;

import java.time.LocalDateTime;
import java.util.Random;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParkingSlot {
  @Builder.Default private final String parkingId = "PAR-" + new Random().nextInt(15);
  private final String carNumber;
  private final LocalDateTime checkInDateTime;
  private final LocalDateTime expiryDateTime;
  private final int extendedCount;
}
