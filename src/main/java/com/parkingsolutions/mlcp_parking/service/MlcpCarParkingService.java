package com.parkingsolutions.mlcp_parking.service;

import com.parkingsolutions.mlcp_parking.dto.BookingDetails;
import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import com.parkingsolutions.mlcp_parking.repo.CarParkingRepo;
import com.parkingsolutions.mlcp_parking.rest.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MlcpCarParkingService {
  @Autowired private CarParkingRepo carParkingrepo;

  public List<ParkingSlot> getAllParkingSlots() {
    List<ParkingSlot> parkingSlots = carParkingrepo.getParkings();
    return parkingSlots;
  }

  public String reserveParking(BookingDetails bookingDetails) {
    ParkingSlot toBeReservedParkingSlot =
        ParkingSlot.builder()
            .carNumber(bookingDetails.getCarNumber())
            .checkInDateTime(LocalDateTime.now())
            .expiryDateTime(LocalDateTime.now().plusHours(bookingDetails.getHours()))
            .build();
    ParkingSlot parkingSlot = carParkingrepo.addCarParkingBooking(toBeReservedParkingSlot);
    return parkingSlot.getParkingId();
  }

  public String updateReservation(BookingDetails bookingDetails, int count) {
    ParkingSlot retrievedParkingSlot =
        carParkingrepo.getBookingBasedOnCarNumber(bookingDetails.getCarNumber());
    if (retrievedParkingSlot == null) {
      throw new NotFoundException("The parking slot you are trying to update is not found");
    }
    // deleting old Parking entry
    carParkingrepo.deleteParkingSlot(bookingDetails.getCarNumber());
    // creating new parking entry
    ParkingSlot updatedParkingSlot =
        ParkingSlot.builder()
            .parkingId(retrievedParkingSlot.getParkingId())
            .carNumber(bookingDetails.getCarNumber())
            .checkInDateTime(retrievedParkingSlot.getCheckInDateTime())
            .expiryDateTime(
                retrievedParkingSlot.getExpiryDateTime().plusHours(bookingDetails.getHours()))
            .extendedCount(count)
            .build();

    ParkingSlot extendedParkingSlot = carParkingrepo.addCarParkingBooking(updatedParkingSlot);
    return extendedParkingSlot.getParkingId();
  }

  public boolean deleteParkingSlot(String parkingId) {
    ParkingSlot parkingSlotToBeDeleted = carParkingrepo.getBookingBasedOnCarNumber(parkingId);
    if (parkingSlotToBeDeleted == null)
      throw new NotFoundException("The parking slot is not found to be deleted");
    return parkingSlotToBeDeleted != null ? carParkingrepo.deleteParkingSlot(parkingId) : false;
  }
}
