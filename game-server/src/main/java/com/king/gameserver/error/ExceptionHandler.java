package com.king.gameserver.error;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ExceptionHandler {

    public void handle(final Throwable throwable, final HttpExchange exchange) {
        try {
            final String message = throwable.getMessage();
            final OutputStream responseBody = exchange.getResponseBody();

            if (throwable instanceof BadRequestException) {
                exchange.sendResponseHeaders(((BadRequestException) throwable).getStatusCode(), message.getBytes().length);
            } else if (throwable instanceof MethodNotAllowedException) {
                exchange.sendResponseHeaders(((MethodNotAllowedException) throwable).getStatusCode(), message.getBytes().length);
            } else if (throwable instanceof NotFoundException) {
                exchange.sendResponseHeaders(((NotFoundException) throwable).getStatusCode(), message.getBytes().length);
            } else {
                exchange.sendResponseHeaders(500, message.length());
            }
            responseBody.write(throwable.getMessage().getBytes());
            responseBody.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
