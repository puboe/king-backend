package com.king.gameserver.error;

public class NotFoundException extends ApiException {

    public NotFoundException(final String resource) {
        super(404, resource + " not found.");
    }
}
