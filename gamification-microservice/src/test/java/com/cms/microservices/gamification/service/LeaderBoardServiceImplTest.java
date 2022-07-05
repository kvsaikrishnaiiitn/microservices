package com.cms.microservices.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cms.microservices.gamification.model.LeaderBoardRow;
import com.cms.microservices.gamification.repository.ScoreCardRepository;
import com.cms.microservices.gamification.service.impl.LeaderBoardServiceImpl;

/**
 * @author moises.macero
 */
public class LeaderBoardServiceImplTest {

	@InjectMocks
	private LeaderBoardServiceImpl leaderBoardService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		// leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
	}

	@Test
	public void retrieveLeaderBoardTest() {
		// given
		Long userId = 1L;
		LeaderBoardRow leaderRow1 = new LeaderBoardRow(userId, 300L);
		List<LeaderBoardRow> expectedLeaderBoard = Collections.singletonList(leaderRow1);
		given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoard);

		// when
		List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

		// then
		assertThat(leaderBoard).isEqualTo(expectedLeaderBoard);
	}
}