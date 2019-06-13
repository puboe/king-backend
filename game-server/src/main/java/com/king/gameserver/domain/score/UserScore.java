package com.king.gameserver.domain.score;

import java.util.Comparator;
import java.util.Objects;

public class UserScore {

    private long userId;
    private int highScore;

    public static final Comparator<UserScore> HIGH_SCORE_COMPARATOR = new Comparator<UserScore>() {
        public int compare(final UserScore score1, final UserScore score2) {
            return score2.highScore - score1.highScore;
        }
    };

    public UserScore(final long userId, final int highScore) {
        this.userId = userId;
        this.highScore = highScore;
    }

    public long getUserId() {
        return userId;
    }

    public int getHighScore() {
        return highScore;
    }

    @Override
    public String toString() {
        return userId + "=" + highScore;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserScore userScore = (UserScore) o;
        return userId == userScore.userId &&
                highScore == userScore.highScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, highScore);
    }
}
