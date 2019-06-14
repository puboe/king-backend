package com.king.gameserver.config;

public final class Configurations {

    public static int SERVER_PORT = 8080;
    public static int HIGH_SCORES_LIMIT = 15;
    public static final int SESSION_KEY_LENGTH = 21;
    public static final int SESSION_VALIDITY_TIME = 10 * 60 * 1000;

    private Configurations() {
    }
}
