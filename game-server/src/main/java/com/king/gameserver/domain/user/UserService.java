package com.king.gameserver.domain.user;

public class UserService {

    private static final int SESSION_VALIDITY_TIME = 10 * 60 * 1000;

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private SessionGenerator sessionDelegate;

    public UserService(final UserRepository userRepository,
                       final SessionRepository sessionRepository,
                       final SessionGenerator sessionDelegate) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.sessionDelegate = sessionDelegate;
    }

    public String createSessionKey(final long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            user = userRepository.saveUser(new User(userId));
        }
        final String sessionKey = sessionDelegate.createSession();
        sessionRepository.saveSession(sessionKey, user);

        return sessionKey;
    }

    public User getAuthenticatedUser(final String sessionKey) {
        final UserSession session = sessionRepository.getUserSessionByKey(sessionKey);
        if (session == null || !isValidSession(session)) {
            sessionRepository.deleteSessionByKey(sessionKey);
            return null;
        }
        return userRepository.getUserById(session.getUserId());
    }

    private boolean isValidSession(final UserSession session) {
        long validity = System.currentTimeMillis() - SESSION_VALIDITY_TIME;
        return session.getTimestamp() >= validity;
    }
}
