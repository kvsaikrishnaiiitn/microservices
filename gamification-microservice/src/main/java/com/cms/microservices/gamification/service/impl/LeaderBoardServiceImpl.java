package com.cms.microservices.gamification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.microservices.gamification.model.LeaderBoardRow;
import com.cms.microservices.gamification.repository.ScoreCardRepository;
import com.cms.microservices.gamification.service.LeaderBoardService;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

	@Autowired
	private ScoreCardRepository scoreCardRepository;

	/*
	 * LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
	 * this.scoreCardRepository = scoreCardRepository; }
	 */

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}
}