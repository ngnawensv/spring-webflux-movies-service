package com.belrose.moviesservice.exception;

public class ReviewsServerException extends RuntimeException{
  private  String message;

  public ReviewsServerException(String message, String message1) {
    super(message);
    this.message = message1;
  }
}
