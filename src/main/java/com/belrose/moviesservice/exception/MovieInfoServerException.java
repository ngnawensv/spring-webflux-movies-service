package com.belrose.moviesservice.exception;

public class MovieInfoServerException extends RuntimeException{
  private  String message;

  public MovieInfoServerException(String message, String message1) {
    super(message);
    this.message = message1;
  }
}
