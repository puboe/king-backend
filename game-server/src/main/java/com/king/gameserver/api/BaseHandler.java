package com.king.gameserver.api;

import com.king.gameserver.error.ExceptionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class BaseHandler {

    private final ExceptionHandler exceptionHandler;

    public BaseHandler(final ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void handle(final HttpExchange exchange) {
        try {
            execute(exchange);
        } catch (final Exception e) {
            exceptionHandler.handle(e, exchange);
        }
    }

    protected abstract void execute(final HttpExchange exchange) throws IOException;


//    protected <T> T readRequest(InputStream is, Class<T> type) {
//        return Try.of(() -> objectMapper.readValue(is, type))
//                .getOrElseThrow(ApplicationExceptions.invalidRequest());
//    }
//
//    protected <T> byte[] writeResponse(T response) {
//        return Try.of(() -> objectMapper.writeValueAsBytes(response))
//                .getOrElseThrow(ApplicationExceptions.invalidRequest());
//    }
//
//    protected static Headers getHeaders(String key, String value) {
//        Headers headers = new Headers();
//        headers.set(key, value);
//        return headers;
//    }
}
