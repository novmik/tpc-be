package com.novmik.tpc.exception;

public class TokenRefreshException extends RuntimeException{
    private final String token;
    private final String message;

    public TokenRefreshException(String token, String message) {
        super(String.format("Не обновился токен [%s]: [%s])", token, message));
        this.token = token;
        this.message = message;
    }
}
