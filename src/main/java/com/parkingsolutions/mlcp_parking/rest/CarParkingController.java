package com.parkingsolutions.mlcp_parking.rest;

import com.parkingsolutions.mlcp_parking.dto.BookingDetails;
import com.parkingsolutions.mlcp_parking.rest.exception.BadInputException;
import com.parkingsolutions.mlcp_parking.service.MlcpCarParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi")
public class CarParkingController {
    @Value("${carparking.size}")
    private int totalSlots;
    @Autowired
    private MlcpCarParkingService mlcpCarParkingService;

    @GetMapping("/parkings")
    public ResponseEntity<?> getAvailableParkingSpaces(){
        int occupiedSlots = mlcpCarParkingService.getAllParkingSlots().size();
        return ResponseEntity.ok().body("Available slots "+ (totalSlots-occupiedSlots));
    }

    @PostMapping("/parkings")
    public ResponseEntity<?> addNewParkingSlot(@RequestBody BookingDetails bookingDetails){
        validate(bookingDetails);
        String parkingId = mlcpCarParkingService.reserveParking(bookingDetails);
        return ResponseEntity.ok().body(parkingId);
    }

    @PutMapping("/parkings")
    public ResponseEntity<?> updateBooking(@RequestBody BookingDetails bookingDetails, @RequestParam(name = "extendingTimes") int updatingCount){
        validate(bookingDetails);
       String parkingId =  mlcpCarParkingService.updateReservation(bookingDetails,updatingCount);
        return ResponseEntity.ok().body(parkingId);
    }

    @DeleteMapping("/parkings/{carNumber}")
    public ResponseEntity deleteBooking(@PathVariable String carNumber){
        boolean isDeleted = mlcpCarParkingService.deleteParkingSlot(carNumber);
        if(isDeleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.internalServerError().build();
    }

    private void validate(BookingDetails bookingDetails){
        if(bookingDetails.getHours() < 0 || bookingDetails.getHours() > 4)
            throw new BadInputException("hours cannot be less than 4 or greater than 8");
    }
}
