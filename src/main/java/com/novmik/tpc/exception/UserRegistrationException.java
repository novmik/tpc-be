package com.novmik.tpc.exception;

public class UserRegistrationException extends RuntimeException {

  private final String user;
  private final String message;

  public UserRegistrationException(final String user, final String message) {
    super(String.format("Failed to register User[%s] : '%s'", user, message));
    this.user = user;
    this.message = message;
  }
}