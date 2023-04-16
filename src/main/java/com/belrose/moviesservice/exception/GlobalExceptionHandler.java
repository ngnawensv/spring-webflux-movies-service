package com.belrose.moviesservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MovieInfoClientException.class)
  public ResponseEntity<String> handlerClientException(MovieInfoClientException ex){
    log.error("Exception Caught in handlerClientException : {} ", ex.getMessage());
    return  ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());

  }

}
