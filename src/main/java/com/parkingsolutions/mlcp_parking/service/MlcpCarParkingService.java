package com.parkingsolutions.mlcp_parking.service;

import com.parkingsolutions.mlcp_parking.dto.BookingDetails;
import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import com.parkingsolutions.mlcp_parking.repo.CarParkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MlcpCarParkingService {
    @Autowired
    private CarParkingRepo carParkingrepo;

    public List<ParkingSlot> getAllParkingSlots() {
        List<ParkingSlot> parkingSlots = carParkingrepo.getParkings();
        return parkingSlots;
    }

    public String reserveParking(BookingDetails bookingDetails) {
        ParkingSlot toBeReservedParkingSlot = ParkingSlot.builder().carNumber(bookingDetails.getCarNumber())
                .checkInDateTime(LocalDateTime.now()).expiryDateTime(LocalDateTime.now().plusHours(bookingDetails.getHours())).build();
        ParkingSlot parkingSlot = carParkingrepo.addCarParkingBooking(toBeReservedParkingSlot);
        return parkingSlot.getParkingId();
    }

    public String updateReservation(BookingDetails bookingDetails, int count) {
        ParkingSlot retrievedParkingSlot = carParkingrepo.getBookingBasedOnCarNumber(bookingDetails.getCarNumber());
        //deleting old Parking entry
        carParkingrepo.deleteParkignSlot(bookingDetails.getCarNumber());
        //creating new parking entry
        ParkingSlot updatedParkingSlot = ParkingSlot.builder().parkingId(retrievedParkingSlot.getParkingId())
                .carNumber(bookingDetails.getCarNumber()).checkInDateTime(retrievedParkingSlot.getCheckInDateTime())
                .expiryDateTime(retrievedParkingSlot.getExpiryDateTime().plusHours(bookingDetails.getHours())).extendedCount(count).build();

        ParkingSlot extendedParkingSlot  = carParkingrepo.addCarParkingBooking(updatedParkingSlot);
        return extendedParkingSlot.getParkingId();
    }

    public boolean deleteParkingSlot(String carNumber) {
        ParkingSlot parkingSlotToBeDeleted = carParkingrepo.getBookingBasedOnCarNumber(carNumber);
        return parkingSlotToBeDeleted!=null?carParkingrepo.deleteParkignSlot(carNumber):false;
    }
}
