package com.king.gameserver.api.router;

import java.util.regex.Pattern;

public final class Routes {

    public static final Pattern USER_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/login($|\\?.*)");
    public static final Pattern SCORE_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/(score|highscorelist)($|\\?.*)");

    private Routes() {
    }
}
