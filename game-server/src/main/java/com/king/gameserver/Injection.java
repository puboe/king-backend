package com.king.gameserver;

import com.king.gameserver.api.score.ScoreController;
import com.king.gameserver.api.user.UserController;
import com.king.gameserver.data.score.InMemoryScoreRepository;
import com.king.gameserver.data.user.InMemorySessionRepository;
import com.king.gameserver.data.user.InMemoryUserRepository;
import com.king.gameserver.domain.score.ScoreRepository;
import com.king.gameserver.domain.score.ScoreService;
import com.king.gameserver.domain.user.SessionGenerator;
import com.king.gameserver.domain.user.SessionRepository;
import com.king.gameserver.domain.user.UserRepository;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.ExceptionHandler;

class Injection {

    private static final SessionGenerator SESSION_GENERATOR = new SessionGenerator();
    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final SessionRepository SESSION_REPOSITORY = new InMemorySessionRepository();
    private static final ScoreRepository SCORE_REPOSITORY = new InMemoryScoreRepository();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY, SESSION_REPOSITORY, SESSION_GENERATOR);
    private static final ScoreService SCORE_SERVICE = new ScoreService(SCORE_REPOSITORY);
    private static final ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler();
    private static final UserController USER_CONTROLLER = new UserController(USER_SERVICE, EXCEPTION_HANDLER);
    private static final ScoreController SCORE_CONTROLLER = new ScoreController(SCORE_SERVICE, USER_SERVICE, EXCEPTION_HANDLER);

    public static UserController provideUserController() {
        return USER_CONTROLLER;
    }

    public static ScoreController provideScoreController() {
        return SCORE_CONTROLLER;
    }

    public static ExceptionHandler provideExceptionHandler() {
        return EXCEPTION_HANDLER;
    }
}
