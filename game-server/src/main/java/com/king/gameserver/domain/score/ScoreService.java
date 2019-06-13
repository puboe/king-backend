package com.king.gameserver.domain.score;

import com.king.gameserver.domain.user.User;

import java.util.List;

public class ScoreService {

    private static final int LIMIT = 15;

    private ScoreRepository scoreRepository;

    public ScoreService(final ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void saveScore(final User user, final int level, final int score) {
        scoreRepository.saveScore(level, user, score);
    }

    public List<UserScore> getHighScores(final int level) {
        return scoreRepository.getHighScoresForLevel(level, LIMIT);
    }
}
