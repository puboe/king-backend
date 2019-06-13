package com.king.gameserver;

import com.king.gameserver.api.user.UserController;
import com.king.gameserver.data.user.InMemorySessionRepository;
import com.king.gameserver.data.user.InMemoryUserRepository;
import com.king.gameserver.domain.user.SessionGenerator;
import com.king.gameserver.domain.user.SessionRepository;
import com.king.gameserver.domain.user.UserRepository;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.ExceptionHandler;

class Injection {

    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final SessionRepository SESSION_REPOSITORY = new InMemorySessionRepository();
    private static final SessionGenerator SESSION_GENERATOR = new SessionGenerator();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY, SESSION_REPOSITORY, SESSION_GENERATOR);
    private static final ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler();
    private static final UserController USER_CONTROLLER = new UserController(USER_SERVICE, EXCEPTION_HANDLER);

    public static UserRepository provideUserRepository() {
        return USER_REPOSITORY;
    }

    public static SessionRepository provideSessionRepository() {
        return SESSION_REPOSITORY;
    }

    public static UserService provideUserService() {
        return USER_SERVICE;
    }

    public static UserController provideUserController() {
        return USER_CONTROLLER;
    }

    public static ExceptionHandler provideExceptionHandler() {
        return EXCEPTION_HANDLER;
    }
}
