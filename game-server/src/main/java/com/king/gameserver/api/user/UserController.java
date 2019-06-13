package com.king.gameserver.api.user;

import com.king.gameserver.api.BaseHandler;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.MethodNotAllowedException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class UserController extends BaseHandler {

    private UserService userService;

    public UserController(final UserService userService, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.userService = userService;
    }

    @Override
    protected void execute(final HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            throw new MethodNotAllowedException(exchange.getRequestMethod());
        }

        handleLogin(exchange);
    }

    private void handleLogin(final HttpExchange exchange) throws IOException {
        final long userId = extractUserId(exchange.getRequestURI().getPath());
        final String session = userService.createSessionKey(userId);

        exchange.sendResponseHeaders(200, session.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(session.getBytes());
        output.flush();
        exchange.close();
    }

    private long extractUserId(final String requestPath) {
        final String[] splits = requestPath.split("/");
        return Long.valueOf(splits[1]);
    }
}
