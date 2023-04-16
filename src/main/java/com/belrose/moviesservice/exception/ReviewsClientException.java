package com.belrose.moviesservice.exception;

public class ReviewsClientException extends RuntimeException{
  private  String message;

  public ReviewsClientException(String message, String message1) {
    super(message);
    this.message = message1;
  }
}
