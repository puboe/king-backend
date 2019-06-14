package com.king.gameserver.api.router;

import com.king.gameserver.api.BaseHandler;
import com.king.gameserver.api.score.ScoreController;
import com.king.gameserver.api.user.UserController;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.NotFoundException;
import com.sun.net.httpserver.HttpExchange;

public class RouteHandler extends BaseHandler {

    private UserController userController;
    private ScoreController scoreController;

    public RouteHandler(final UserController userController,
                        final ScoreController scoreController,
                        final ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.userController = userController;
        this.scoreController = scoreController;
    }

    @Override
    protected void execute(final HttpExchange exchange) {
        final String requestPath = exchange.getRequestURI().getPath();

        if (Routes.USER_PATH_PATTERN.matcher(requestPath).matches()) {
            userController.handle(exchange);
        } else if (Routes.SCORE_PATH_PATTERN.matcher(requestPath).matches()) {
            scoreController.handle(exchange);
        } else {
            throw new NotFoundException(requestPath);
        }
    }
}
