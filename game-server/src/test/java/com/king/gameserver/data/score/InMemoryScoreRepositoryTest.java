package com.king.gameserver.data.score;

import com.king.gameserver.domain.score.ScoreRepository;
import com.king.gameserver.domain.score.UserScore;
import com.king.gameserver.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * {@link InMemoryScoreRepository} test class.
 */
public class InMemoryScoreRepositoryTest {

    private ScoreRepository repository;

    @Before
    public void setUp() {
        repository = new InMemoryScoreRepository();
    }

    @Test
    public void saveScore_shouldAppearInHighScoresForThatLevel() {
        final int level = 1;
        final User user = new User(123);
        final int score = 10;

        repository.saveScore(level, user, score);

        final List<UserScore> scoresForLevel1 = repository.getHighScoresForLevel(level, 15);
        assertEquals(1, scoresForLevel1.size());
        assertEquals(user.getUserId(), scoresForLevel1.get(0).getUserId());
        assertEquals(score, scoresForLevel1.get(0).getHighScore());
        assertNull(repository.getHighScoresForLevel(level + 1, 15));
    }

    @Test
    public void getHighScoresForLevel_withLimit1_shouldReturnOneResultForLevel() {
        final int level = 1;
        final int limit = 1;
        repository.saveScore(level, new User(124), 9);
        repository.saveScore(level, new User(123), 10);
        repository.saveScore(level, new User(124), 12);
        repository.saveScore(level, new User(125), 11);

        final List<UserScore> scoresForLevel1 = repository.getHighScoresForLevel(level, limit);

        assertEquals(1, scoresForLevel1.size());
        assertEquals(124, scoresForLevel1.get(0).getUserId());
        assertEquals(12, scoresForLevel1.get(0).getHighScore());
    }
}
