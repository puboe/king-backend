package com.king.gameserver.data.user;

import com.king.gameserver.domain.user.SessionRepository;
import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * {@link InMemorySessionRepository} test class.
 */
public class InMemorySessionRepositoryTest {

    private SessionRepository repository;

    @Before
    public void setUp() {
        repository = new InMemorySessionRepository();
    }

    @Test
    public void saveSession_shouldGetSameSession() {
        final User user = new User(123);

        repository.saveSession("ABC", user);

        assertEquals(new UserSession("ABC", 123), repository.getUserSessionByKey("ABC"));
    }

    @Test
    public void deleteUser_shouldNotGetSameUser() {
        final User newUser = new User(123);
        repository.saveSession("ABC", newUser);

        repository.deleteSessionByKey("ABC");

        assertNull(repository.getUserSessionByKey("ABC"));
    }
}
