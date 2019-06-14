package com.king.gameserver.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link UserService} test class.
 */
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SessionGenerator generator;
    private UserService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UserService(userRepository, sessionRepository, generator);
    }

    @Test
    public void createSessionKey_withNotExistingUser_shouldCreateUserAndAssignSession() {
        final User newUser = new User(123);
        when(userRepository.getUserById(123)).thenReturn(null);
        when(userRepository.saveUser(newUser)).thenReturn(newUser);
        when(generator.createSession()).thenReturn("ABC");

        final String sessionKey = service.createSessionKey(123);

        assertEquals("ABC", sessionKey);
        verify(sessionRepository).saveSession(sessionKey, newUser);
    }

    @Test
    public void createSessionKey_withExistingUser_shouldAssignSession() {
        final User newUser = new User(123);
        when(userRepository.getUserById(123)).thenReturn(newUser);
        when(generator.createSession()).thenReturn("ABC");

        final String sessionKey = service.createSessionKey(123);

        assertEquals("ABC", sessionKey);
        verify(sessionRepository).saveSession(sessionKey, newUser);
    }

    @Test
    public void getAuthenticatedUser_withNullSessionKey_shouldReturnNull() {
        final User user = service.getAuthenticatedUser(null);

        assertNull(user);
    }

    @Test
    public void getAuthenticatedUser_withValidSessionKey_shouldReturnUser() {
        UserSession session = new UserSession("ABC", 123);
        when(sessionRepository.getUserSessionByKey("ABC")).thenReturn(session);
        when(userRepository.getUserById(123)).thenReturn(new User(123));

        final User user = service.getAuthenticatedUser("ABC");

        assertEquals(session.getUserId(), user.getUserId());
        verify(userRepository).getUserById(123);
    }

    @Test
    public void getAuthenticatedUser_withInvalidSessionKey_shouldReturnNull() {
        when(sessionRepository.getUserSessionByKey("ABC")).thenReturn(null);

        final User user = service.getAuthenticatedUser("ABC");

        assertNull(user);
        verify(sessionRepository).deleteSessionByKey("ABC");
    }
}
