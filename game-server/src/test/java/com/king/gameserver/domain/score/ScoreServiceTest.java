package com.king.gameserver.domain.score;

import com.king.gameserver.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


/**
 * {@link ScoreService} test class.
 */
public class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;
    private ScoreService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new ScoreService(scoreRepository);
    }

    @Test
    public void saveScore_shouldCallRepository() {
        final User user = new User(123);
        final int level = 2;
        final int score = 10;

        service.saveScore(user, level, score);

        verify(scoreRepository).saveScore(level, user, score);
    }

    @Test
    public void getHighScores_shouldCallRepository() {
        final int level = 2;

        service.getHighScores(level);

        verify(scoreRepository).getHighScoresForLevel(level, 15);
    }
}
