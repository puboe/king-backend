package com.king.gameserver.error;

public class BadRequestException extends ApiException {

    public BadRequestException(final String message) {
        super(400, message);
    }
}
