package com.king.gameserver.api.user;

import com.king.gameserver.api.BaseHandler;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.MethodNotAllowedException;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public class UserController extends BaseHandler {

    private UserService userService;

    public UserController(final UserService userService, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.userService = userService;
    }

    @Override
    protected void execute(final HttpExchange exchange) throws Exception {
        if (!exchange.getRequestMethod().equals("GET")) {
            throw new MethodNotAllowedException(exchange.getRequestMethod());
        }

        final long userId = 12345;
        final String session = userService.createSessionKey(userId);

        exchange.sendResponseHeaders(200, session.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(session.getBytes());
        output.flush();
        exchange.close();
    }
}
