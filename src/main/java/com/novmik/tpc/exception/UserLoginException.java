package com.novmik.tpc.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

@ResponseStatus(EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {

    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}