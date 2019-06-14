package com.king.gameserver.api.user;

import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.MethodNotAllowedException;
import com.king.gameserver.util.TestUtil;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link UserController} test class.
 */
public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ExceptionHandler exceptionHandler;
    private UserController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new UserController(userService, exceptionHandler);
    }

    @Test(expected = MethodNotAllowedException.class)
    public void execute_withWrongMethod_shouldThrowException() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/123/login", "POST", "");

        controller.execute(exchange);
    }

    @Test
    public void execute_withGetMethod_shouldReturnSessionKey() throws Exception {
        final HttpExchange exchange = TestUtil.mockHttpExchange("/123/login", "GET", "");
        when(userService.createSessionKey(123)).thenReturn("ABC");

        controller.execute(exchange);

        final ByteArrayOutputStream out = (ByteArrayOutputStream) exchange.getResponseBody();
        final String responseSessionKey = new String(out.toByteArray());
        assertEquals("ABC", responseSessionKey);
    }
}
