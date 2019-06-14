package com.king.gameserver.api.router;

import com.king.gameserver.api.score.ScoreController;
import com.king.gameserver.api.user.UserController;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.NotFoundException;
import com.king.gameserver.util.TestUtil;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RouteHandlerTest {

    @Mock
    private UserController userController;
    @Mock
    private ScoreController scoreController;
    @Mock
    private ExceptionHandler exceptionHandler;
    private RouteHandler routeHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        routeHandler = new RouteHandler(userController, scoreController, exceptionHandler);
    }

    @Test
    public void execute_withUserPath_shouldDispatchToUserController() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/login", "GET", "");

        routeHandler.execute(exchange);

        verify(userController).handle(exchange);
    }

    @Test
    public void execute_withScorePath_shouldDispatchToScoreController() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/score", "POST", "123");

        routeHandler.execute(exchange);

        verify(scoreController).handle(exchange);
    }

    @Test(expected = NotFoundException.class)
    public void execute_withWrongPath_shouldThrowException() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/notfound", "GET", "");

        routeHandler.execute(exchange);
    }
}
