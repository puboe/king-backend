package com.king.gameserver.error;

public class MethodNotAllowedException extends ApiException {

    public MethodNotAllowedException(final String method) {
        super(405, method + " method not allowed.");
    }
}
