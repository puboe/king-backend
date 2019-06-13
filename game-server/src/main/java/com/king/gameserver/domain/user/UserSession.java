package com.king.gameserver.domain.user;

import java.util.Objects;

public class UserSession {

    private long userId;
    private String sessionKey;
    private long timestamp;

    public UserSession(final String sessionKey, final long userId) {
        this.sessionKey = sessionKey;
        this.userId = userId;
        this.timestamp = System.currentTimeMillis();
    }

    public long getUserId() {
        return userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserSession that = (UserSession) o;
        return timestamp == that.timestamp &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(sessionKey, that.sessionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionKey, timestamp);
    }
}
