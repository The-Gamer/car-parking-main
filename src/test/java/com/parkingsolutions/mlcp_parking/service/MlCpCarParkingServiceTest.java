package com.parkingsolutions.mlcp_parking.service;

import com.parkingsolutions.mlcp_parking.dto.BookingDetails;
import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import com.parkingsolutions.mlcp_parking.repo.CarParkingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MlCpCarParkingServiceTest {
    @Mock private CarParkingRepo carParkingRepo;
    @InjectMocks
    private MlcpCarParkingService mlcpCarParkingService;

    @BeforeEach
     void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Test to get all available the  parking slots.")
    @Test
    void get_CarParkingSlots(){
        //arrange
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        int size = 10;
        while ( size>0){
            ParkingSlot parkingSlot = ParkingSlot.builder().build();
            parkingSlots.add(parkingSlot);
            size--;
        }
        when(carParkingRepo.getParkings()).thenReturn(parkingSlots);
        //act
        List<ParkingSlot> parkingSlotList = mlcpCarParkingService.getAllParkingSlots();
        //assert
        assertTrue(parkingSlotList.size() == 10);
    }

    @DisplayName("Test to reserve a parking slot.")
    @Test
    void post_ParkingSlot(){
        //arrange
        ParkingSlot parkingSlot = ParkingSlot.builder().build();
        BookingDetails bookingDetails = new BookingDetails("KA-03-2512",1);
        when(carParkingRepo.addCarParkingBooking(any())).thenReturn(parkingSlot);
        //act
        String parkingSlotId = mlcpCarParkingService.reserveParking(bookingDetails);
        //assert
        assertTrue(parkingSlotId!=null);
        assertEquals(parkingSlot.getParkingId(),parkingSlotId);
    }

    @DisplayName("Update a parking slot, steps are select, delete, new insert")
    @Test
    void update_ParkingSlotTime(){
        //arrange
        BookingDetails bookingDetails = new BookingDetails("KA-03-2512",1);
        int extendedParkingSlotTimes = 1;
        ParkingSlot parkingSlot = ParkingSlot.builder().carNumber(bookingDetails.getCarNumber())
                .checkInDateTime(LocalDateTime.now()).expiryDateTime(LocalDateTime.now().plusHours(bookingDetails.getHours())).build();
        ParkingSlot parkingSlotUpdated = ParkingSlot.builder().parkingId(parkingSlot.getParkingId()).carNumber(bookingDetails.getCarNumber())
                .checkInDateTime(LocalDateTime.now()).expiryDateTime(LocalDateTime.now().plusHours(bookingDetails.getHours())).extendedCount(1).build();
        when(carParkingRepo.getBookingBasedOnCarNumber(anyString())).thenReturn(parkingSlot);
        //delete previous entry for car parking
        when(carParkingRepo.deleteParkignSlot(anyString())).thenReturn(true);
        when(carParkingRepo.addCarParkingBooking(any())).thenReturn(parkingSlotUpdated);
        //act
        String parkingSlotId = mlcpCarParkingService.updateReservation(bookingDetails,extendedParkingSlotTimes);
        //assert
        assertTrue(parkingSlotId != null);
        assertEquals(parkingSlotId,parkingSlotUpdated.getParkingId());
    }

    @DisplayName("To delete first retrieve and then delete")
    @Test
    void delete_ParkingSlot(){
        //arrange
        ParkingSlot parkingSlot = ParkingSlot.builder().build();
        when(carParkingRepo.getBookingBasedOnCarNumber(anyString())).thenReturn(parkingSlot);
        when(carParkingRepo.deleteParkignSlot(anyString())).thenReturn(true);
        String parkingId = "PAR-01";
        //act
        boolean isDeleted = mlcpCarParkingService.deleteParkingSlot(parkingSlot.getParkingId());
        //assert
        assertTrue(isDeleted);
    }
}
