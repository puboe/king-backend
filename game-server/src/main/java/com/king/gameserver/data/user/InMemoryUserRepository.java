package com.king.gameserver.data.user;

import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryUserRepository implements UserRepository {

    final ConcurrentMap<Long, User> userStorage = new ConcurrentHashMap<>();

    @Override
    public User saveUser(final User user) {
        userStorage.put(user.getUserId(), user);
        return user;
    }

    @Override
    public void deleteUserById(final long userId) {
        userStorage.remove(userId);
    }

    @Override
    public User getUserById(final long userId) {
        return userStorage.get(userId);
    }
}
