package com.king.gameserver.domain.score;

import com.king.gameserver.domain.user.User;

import java.util.List;

public interface ScoreRepository {

    void saveScore(final int level, final User user, final int score);

    List<UserScore> getHighScoresForLevel(final int level, final int limit);
}
