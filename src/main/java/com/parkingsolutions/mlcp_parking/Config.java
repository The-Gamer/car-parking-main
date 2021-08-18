package com.parkingsolutions.mlcp_parking;

import com.parkingsolutions.mlcp_parking.repo.CarParkingRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public CarParkingRepo getCarParkingRepo() {
    CarParkingRepo carParkingRepo = new CarParkingRepo();
    return carParkingRepo;
  }
}
