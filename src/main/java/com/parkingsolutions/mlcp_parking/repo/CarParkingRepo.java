package com.parkingsolutions.mlcp_parking.repo;

import com.parkingsolutions.mlcp_parking.entity.ParkingSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class CarParkingRepo {
    @Value("${carparking.size}")
    private int size;
    private static List<ParkingSlot> parkingSlots = null;

    public CarParkingRepo(){
        parkingSlots = new ArrayList<>(size);
    }

    public List<ParkingSlot> getParkings(){
        if(parkingSlots==null){
            parkingSlots = new ArrayList<>(size);
        }
        return parkingSlots;
    }

    public ParkingSlot addCarParkingBooking(ParkingSlot parkingSlot) {
        parkingSlots.add(parkingSlot);
        return parkingSlot;
    }

    public ParkingSlot getBookingBasedOnCarNumber(String carNumber) {
        ParkingSlot parkingSlot = parkingSlots.stream().filter(e -> e.getCarNumber().equals(carNumber)).findFirst().orElse(null);
        return parkingSlot;
    }

    public boolean deleteParkignSlot(String carNumber) {
        ParkingSlot parkingSlot = getBookingBasedOnCarNumber(carNumber);
        return parkingSlots.remove(parkingSlot);
    }

}
