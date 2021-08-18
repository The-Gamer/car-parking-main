package com.parkingsolutions.mlcp_parking.repo;

import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class CarParkingRepo {
  @Value("${carparking.size}")
  private int size;

  private static List<ParkingSlot> parkingSlots = null;

  public CarParkingRepo() {
    parkingSlots = new ArrayList<>(size);
  }

  public List<ParkingSlot> getParkings() {
    return parkingSlots;
  }

  public ParkingSlot addCarParkingBooking(ParkingSlot parkingSlot) {
    parkingSlots.add(parkingSlot);
    return parkingSlot;
  }

  public ParkingSlot getBookingBasedOnCarNumber(String parkingId) {
    ParkingSlot parkingSlot =
        parkingSlots.stream()
            .filter(e -> e.getParkingId().equals(parkingId))
            .findFirst()
            .orElse(null);
    return parkingSlot;
  }

  public boolean deleteParkingSlot(String parkingId) {
    ParkingSlot parkingSlot = getBookingBasedOnCarNumber(parkingId);
    return parkingSlots.remove(parkingSlot);
  }
}
