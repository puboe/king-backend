package com.king.gameserver.data.user;

import com.king.gameserver.domain.user.SessionRepository;
import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserSession;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemorySessionRepository implements SessionRepository {

    private ConcurrentMap<String, UserSession> sessionStorage = new ConcurrentHashMap<>();

    @Override
    public void saveSession(final String key, final User user) {
        sessionStorage.put(key, new UserSession(key, user.getUserId()));
    }

    @Override
    public void deleteSessionByKey(final String key) {
        sessionStorage.remove(key);
    }

    @Override
    public UserSession getUserSessionByKey(final String key) {
        return sessionStorage.get(key);
    }
}
