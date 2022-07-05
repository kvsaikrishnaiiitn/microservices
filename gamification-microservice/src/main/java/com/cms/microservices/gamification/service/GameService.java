package com.cms.microservices.gamification.service;

import com.cms.microservices.gamification.model.GameStats;

public interface GameService {

	GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);

	GameStats retrieveStatsForUser(Long userId);

}
