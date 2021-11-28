package com.novmik.tpc.exception;

public class UpdatePasswordException extends RuntimeException{
    private final String user;
    private final String message;

    public UpdatePasswordException(String user, String message) {
        super(String.format("Couldn't update password for [%s]: [%s])", user, message));
        this.user = user;
        this.message = message;
    }
}
