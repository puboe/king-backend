package com.king.gameserver.data.user;

import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * {@link InMemoryUserRepository} test class.
 */
public class InMemoryUserRepositoryTest {

    private UserRepository repository;

    @Before
    public void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    public void saveUser_shouldGetSameUser() {
        final User newUser = new User(123);

        repository.saveUser(newUser);

        assertEquals(newUser, repository.getUserById(newUser.getUserId()));
    }

    @Test
    public void deleteUser_shouldNotGetSameUser() {
        final User newUser = new User(123);
        repository.saveUser(newUser);

        repository.deleteUserById(newUser.getUserId());

        assertNull(repository.getUserById(newUser.getUserId()));
    }
}
