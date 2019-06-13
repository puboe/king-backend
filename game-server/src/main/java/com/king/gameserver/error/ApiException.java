package com.king.gameserver.error;

abstract class ApiException extends RuntimeException {

    private final int statusCode;

    public ApiException(final int statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
