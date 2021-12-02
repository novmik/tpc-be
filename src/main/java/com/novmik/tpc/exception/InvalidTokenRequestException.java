package com.novmik.tpc.exception;

public class InvalidTokenRequestException extends RuntimeException{

    private final String tokenType;
    private final String token;
    private final String message;

    public InvalidTokenRequestException(final String tokenType,
                                        final String token,
                                        final String message) {
        super(String.format("%s: [%s] token: [%s] ", message, tokenType, token));
        this.tokenType = tokenType;
        this.token = token;
        this.message = message;
    }
}
