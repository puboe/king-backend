package com.king.gameserver.domain.user;

import java.security.SecureRandom;

public class SessionGenerator {

    private static final int SESSION_KEY_LENGTH = 21;
    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public String createSession() {
        return randomString(SESSION_KEY_LENGTH);
    }

    private String randomString(final int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(CHARSET.charAt(rnd.nextInt(CHARSET.length())));
        return sb.toString();
    }
}
