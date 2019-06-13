package com.king.gameserver.data.score;

import com.king.gameserver.domain.score.ScoreRepository;
import com.king.gameserver.domain.score.UserScore;
import com.king.gameserver.domain.user.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class InMemoryScoreRepository implements ScoreRepository {

    private ConcurrentMap<Integer, ConcurrentMap<Long, UserScore>> scoreStorage = new ConcurrentHashMap<>();

    @Override
    public void saveScore(final int level, final User user, final int score) {
        // Check if level is created.
        if (scoreStorage.get(level) == null) {
            // Create level.
            scoreStorage.put(level, new ConcurrentHashMap<>());
        }
        // Save high score.
        saveHighScore(level, user.getUserId(), score);
    }

    @Override
    public List<UserScore> getHighScoresForLevel(final int level, final int limit) {
        final ConcurrentMap<Long, UserScore> scores = scoreStorage.get(level);
        if (scores == null || scores.isEmpty()) {
            return null;
        }
        return getHighScores(scores, limit);
    }

    private void saveHighScore(final int level, final long userId, final int score) {
        // Get scores for level.
        final ConcurrentMap<Long, UserScore> scores = scoreStorage.get(level);
        // Get current high score for user.
        final UserScore currentScore = scores.get(userId);
        if (currentScore == null || currentScore.getHighScore() < score) {
            // Update high score for user.
            scores.put(userId, new UserScore(userId, score));
        }
    }

    private List<UserScore> getHighScores(final ConcurrentMap<Long, UserScore> scores, final int limit) {
        return scores.values().stream()
                .sorted(UserScore.HIGH_SCORE_COMPARATOR)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
