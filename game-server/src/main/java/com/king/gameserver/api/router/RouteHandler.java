package com.king.gameserver.api.router;

import com.king.gameserver.api.BaseHandler;
import com.king.gameserver.api.user.UserController;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.NotFoundException;
import com.sun.net.httpserver.HttpExchange;

import java.util.regex.Pattern;

public class RouteHandler extends BaseHandler {

    private static final Pattern USER_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/login($|\\?.*)");
    private static final Pattern SCORE_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/(score|highscorelist)($|\\?.*)");

    private UserController userController;

    public RouteHandler(final UserController userController, final ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.userController = userController;
    }

    @Override
    protected void execute(final HttpExchange exchange) {
        final String requestPath = exchange.getRequestURI().getPath();

        if (USER_PATH_PATTERN.matcher(requestPath).matches()) {
            userController.handle(exchange);
        } else {
            throw new NotFoundException(requestPath);
        }
    }

}
