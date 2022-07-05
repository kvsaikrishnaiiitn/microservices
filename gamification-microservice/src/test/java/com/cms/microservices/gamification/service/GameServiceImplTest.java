package com.cms.microservices.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cms.microservices.gamification.client.MultiplicationResultAttemptClient;
import com.cms.microservices.gamification.client.dto.MultiplicationResultAttempt;
import com.cms.microservices.gamification.model.Badge;
import com.cms.microservices.gamification.model.BadgeCard;
import com.cms.microservices.gamification.model.GameStats;
import com.cms.microservices.gamification.model.ScoreCard;
import com.cms.microservices.gamification.repository.BadgeCardRepository;
import com.cms.microservices.gamification.repository.ScoreCardRepository;
import com.cms.microservices.gamification.service.impl.GameServiceImpl;

/**
 * @author moises.macero
 */
public class GameServiceImplTest {

	@InjectMocks
	private GameServiceImpl gameServiceImpl;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Mock
	private BadgeCardRepository badgeCardRepository;

	@Mock
	private MultiplicationResultAttemptClient multiplicationClient;

	@Before
	public void setUp() {
		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);

		// Common given - attempt does not contain a lucky number by default
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("cms", 10, 20, 200, true);
		given(multiplicationClient.retrieveMultiplicationResultAttemptbyId(Mockito.anyLong())).willReturn(attempt);
	}

	@Test
	public void processFirstCorrectAttemptTest() {
		// given
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		// this repository will return the just-won score card
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

		// when
		GameStats iteration = gameServiceImpl.newAttemptForUser(userId, attemptId, true);

		// assert - should score one card and win the badge FIRST_WON
		assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
		assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
	}

	@Test
	public void processCorrectAttemptForScoreBadgeTest() {
		// given
		Long userId = 1L;
		Long attemptId = 29L;
		int totalScore = 100;
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		// this repository will return the just-won score card
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(10, userId));
		// the first won badge is already there
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.singletonList(firstWonBadge));

		// when
		GameStats iteration = gameServiceImpl.newAttemptForUser(userId, attemptId, true);

		// assert - should score one card and win the badge BRONZE
		assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
	}

	@Test
	public void processCorrectAttemptForLuckyNumberBadgeTest() {// given
		Long userId = 1L;
		Long attemptId = 29L;
		int totalScore = 10;
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		// this repository will return the just-won score card
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(1, userId));
		// the first won badge is already there
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.singletonList(firstWonBadge));
		// the attempt includes the lucky number
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("john_doe", 42, 10, 420, true);
		given(multiplicationClient.retrieveMultiplicationResultAttemptbyId(attemptId)).willReturn(attempt);

		// when
		GameStats iteration = gameServiceImpl.newAttemptForUser(userId, attemptId, true);

		// assert - should score one card and win the badge LUCKY NUMBER
		assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(iteration.getBadges()).containsOnly(Badge.LUCKY_NUMBER);
	}

	@Test
	public void processWrongAttemptTest() {
		// given
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		// this repository will return the just-won score card
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

		// when
		GameStats iteration = gameServiceImpl.newAttemptForUser(userId, attemptId, false);

		// assert - shouldn't score anything
		assertThat(iteration.getScore()).isEqualTo(0);
		assertThat(iteration.getBadges()).isEmpty();
	}

	@Test
	public void retrieveStatsForUserTest() {
		// given
		Long userId = 1L;
		int totalScore = 1000;
		BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.singletonList(badgeCard));

		// when
		GameStats stats = gameServiceImpl.retrieveStatsForUser(userId);

		// assert - should score one card and win the badge FIRST_WON
		assertThat(stats.getScore()).isEqualTo(totalScore);
		assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
	}

	private List<ScoreCard> createNScoreCards(int n, Long userId) {
		return IntStream.range(0, n).mapToObj(i -> new ScoreCard(userId, (long) i)).collect(Collectors.toList());
	}
}