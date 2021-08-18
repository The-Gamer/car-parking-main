package com.parkingsolutions.mlcp_parking.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadInputException extends RuntimeException {
  public BadInputException(String s) {
    super(s);
  }
}
