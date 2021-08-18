package com.parkingsolutions.mlcp_parking.repo;

import static org.junit.jupiter.api.Assertions.*;

import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class CarParkingRepoTest {
  @InjectMocks private CarParkingRepo carParkingRepo;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(carParkingRepo, "size", 10);
  }

  @Test
  void get_ParkingSlots() {
    assertNotNull(carParkingRepo.getParkings());
  }

  @Test
  void add_NewParkingSlot() {
    // arrange
    ParkingSlot parkingSlot =
        ParkingSlot.builder()
            .carNumber("abc-112+")
            .checkInDateTime(LocalDateTime.now())
            .expiryDateTime(LocalDateTime.now().plusHours(4))
            .build();

    // act
    ParkingSlot addedSlot = carParkingRepo.addCarParkingBooking(parkingSlot);
    // assert
    assertNotNull(addedSlot);
    assertEquals(parkingSlot.getParkingId(), addedSlot.getParkingId());
    assertEquals(1, carParkingRepo.getParkings().size());
  }

  @Test
  void get_parkingSlotFromCarNumber() {
    // arrange
    ParkingSlot parkingSlot = ParkingSlot.builder().carNumber("KA-02-MEOW-MEOW").build();
    // act;
    carParkingRepo.addCarParkingBooking(parkingSlot);
    ParkingSlot foundSlot = carParkingRepo.getBookingBasedOnCarNumber(parkingSlot.getParkingId());
    // assert
    assertEquals(parkingSlot.getParkingId(), foundSlot.getParkingId());
  }

  @Test
  void delete_ParkingSlot() {
    // arrange
    String carNumber = "KA-02-MEOW";
    String carNumber2 = "KA-02-MAOW";

    ParkingSlot parkingSlot1 = ParkingSlot.builder().carNumber(carNumber2).build();
    ParkingSlot parkingSlot2 = ParkingSlot.builder().carNumber(carNumber).build();
    carParkingRepo.addCarParkingBooking(parkingSlot1);
    carParkingRepo.addCarParkingBooking(parkingSlot2);
    // act
    boolean isDeleted = carParkingRepo.deleteParkingSlot(parkingSlot1.getParkingId());
    // assert
    assertTrue(isDeleted);
    // as the previous parking slot got deleted.
    assertTrue(carParkingRepo.getParkings().size() == 1);
  }
}
