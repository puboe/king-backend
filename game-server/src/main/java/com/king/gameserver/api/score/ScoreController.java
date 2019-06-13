package com.king.gameserver.api.score;

import com.king.gameserver.api.BaseHandler;
import com.king.gameserver.domain.score.ScoreService;
import com.king.gameserver.domain.score.UserScore;
import com.king.gameserver.domain.user.User;
import com.king.gameserver.domain.user.UserService;
import com.king.gameserver.error.BadRequestException;
import com.king.gameserver.error.ExceptionHandler;
import com.king.gameserver.error.MethodNotAllowedException;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ScoreController extends BaseHandler {

    private static final Pattern SCORE_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/score($|\\?.*)");
    private static final Pattern HIGH_SCORE_PATH_PATTERN = Pattern.compile("\\/(\\d+)\\/highscorelist($|\\?.*)");

    private ScoreService scoreService;
    private UserService userService;

    public ScoreController(final ScoreService scoreService,
                           final UserService userService,
                           final ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.scoreService = scoreService;
        this.userService = userService;
    }

    @Override
    protected void execute(final HttpExchange exchange) throws IOException {
        final String requestPath = exchange.getRequestURI().getPath();

        if (exchange.getRequestMethod().equals("GET") && HIGH_SCORE_PATH_PATTERN.matcher(requestPath).matches()) {
            handleHighScoresRequest(exchange);
        } else if (exchange.getRequestMethod().equals("POST") && SCORE_PATH_PATTERN.matcher(requestPath).matches()) {
            handlePostScoreRequest(exchange);
        } else {
            throw new MethodNotAllowedException(exchange.getRequestMethod());
        }
    }

    private void handlePostScoreRequest(final HttpExchange exchange) throws IOException {
        final String sessionKey = extractSessionKey(exchange);
        final User user = userService.getAuthenticatedUser(sessionKey);
        if (user == null) {
            throw new BadRequestException("Invalid session key.");
        }
        final int score;
        try {
            score = extractScore(exchange);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid score value");
        }
        final int level = extractLevel(exchange.getRequestURI().getPath());
        scoreService.saveScore(user, level, score);

        exchange.sendResponseHeaders(200, 0);
        final OutputStream output = exchange.getResponseBody();
        output.close();
        exchange.close();
    }

    private void handleHighScoresRequest(final HttpExchange exchange) throws IOException {
        final int level = extractLevel(exchange.getRequestURI().getPath());
        final List<UserScore> highScores = scoreService.getHighScores(level);

        final String response;
        if (highScores == null) {
            response = "";
        } else {
            response = highScoresAsCsv(highScores);
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        final OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.close();
        exchange.close();
    }

    private String extractSessionKey(final HttpExchange exchange) {
        final Map<String, List<String>> query = splitQuery(exchange.getRequestURI().getQuery());
        return query.get("sessionkey").get(0);
    }

    private int extractLevel(final String requestPath) {
        final String[] splits = requestPath.split("/");
        return Integer.parseInt(splits[1]);
    }

    private int extractScore(final HttpExchange exchange) throws IOException {
        final InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.US_ASCII);
        final BufferedReader br = new BufferedReader(isr);
        return Integer.parseInt(br.readLine());
    }

    private String highScoresAsCsv(final List<UserScore> highScores) {
        final StringBuilder builder = new StringBuilder();
        for (final UserScore score : highScores) {
            builder.append(score.toString() + ",");
        }
        // Delete last comma.
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }
}
