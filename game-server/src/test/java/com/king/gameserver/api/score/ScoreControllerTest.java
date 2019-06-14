package com.king.gameserver.api.score;

import com.king.gameserver.domain.score.ScoreService;
import com.king.gameserver.domain.score.UserScore;
import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.BadRequestException;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.MethodNotAllowedException;
import com.king.gameserver.util.TestUtil;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link ScoreController} test class.
 */
public class ScoreControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ScoreService scoreService;
    @Mock
    private ExceptionHandler exceptionHandler;
    private ScoreController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ScoreController(scoreService, userService, exceptionHandler);
    }

    @Test(expected = MethodNotAllowedException.class)
    public void execute_withWrongMethod_shouldThrowException() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/123/score", "GET", "");

        controller.execute(exchange);
    }

    @Test
    public void execute_withPostMethodAndValidSessionKey_shouldSaveScore() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/score?sessionkey=ABC", "POST", "1500");
        final User user = new User(123);
        when(userService.getAuthenticatedUser("ABC")).thenReturn(user);

        controller.execute(exchange);

        verify(scoreService).saveScore(user, 1, 1500);
    }

    @Test(expected = BadRequestException.class)
    public void execute_withPostMethodAndInvalidSessionKey_shouldThrowException() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/score?sessionkey=ABC", "POST", "1500");
        when(userService.getAuthenticatedUser("ABC")).thenReturn(null);

        controller.execute(exchange);
    }

    @Test(expected = BadRequestException.class)
    public void execute_withPostMethodAndInvalidScore_shouldThrowException() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/score?sessionkey=ABC", "POST", "abc");
        final User user = new User(123);
        when(userService.getAuthenticatedUser("ABC")).thenReturn(user);

        controller.execute(exchange);
    }

    @Test
    public void execute_withGetMethodAndHighScores_shouldReturnListOfScores() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/highscorelist", "GET", "");
        final List<UserScore> scores = new ArrayList<>();
        scores.add(new UserScore(2, 20));
        scores.add(new UserScore(1, 10));
        when(scoreService.getHighScores(1)).thenReturn(scores);

        controller.execute(exchange);

        final ByteArrayOutputStream out = (ByteArrayOutputStream) exchange.getResponseBody();
        final String responseSessionKey = new String(out.toByteArray());
        assertEquals("2=20,1=10", responseSessionKey);
    }

    @Test
    public void execute_withGetMethodAndNoHighScores_shouldReturnEmptyResponse() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/1/highscorelist", "GET", "");
        when(scoreService.getHighScores(1)).thenReturn(null);

        controller.execute(exchange);

        final ByteArrayOutputStream out = (ByteArrayOutputStream) exchange.getResponseBody();
        final String responseSessionKey = new String(out.toByteArray());
        assertEquals("", responseSessionKey);
    }
}
