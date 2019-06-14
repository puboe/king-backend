package com.king.gameserver.domain.user;

import com.king.gameserver.config.Configurations;

import java.security.SecureRandom;

public class SessionGenerator {

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public String createSession() {
        return randomString(Configurations.SESSION_KEY_LENGTH);
    }

    private String randomString(final int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(CHARSET.charAt(rnd.nextInt(CHARSET.length())));
        return sb.toString();
    }
}
