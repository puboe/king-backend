package com.king.gameserver.domain.user;

public interface SessionRepository {

    void saveSession(String key, User user);

    void deleteSessionByKey(String key);

    UserSession getUserSessionByKey(String key);
}
