package com.parkingsolutions.mlcp_parking.dto;

import lombok.Value;

@Value
public class BookingDetails {
  private final String carNumber;
  private final int hours;
}
