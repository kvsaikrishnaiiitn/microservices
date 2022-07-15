package com.cms.microservices.gamification.service;

import com.cms.microservices.gamification.model.GameStats;
import com.cms.microservices.gamification.model.ScoreCard;

public interface GameService {

	GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);

	GameStats retrieveStatsForUser(Long userId);

	ScoreCard getScoreForAttempt(Long attemptId);

}
