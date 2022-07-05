package com.cms.microservices.gamification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.microservices.gamification.model.LeaderBoardRow;
import com.cms.microservices.gamification.service.LeaderBoardService;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

	@Autowired
	private LeaderBoardService leaderBoardService;

	/*
	 * public LeaderBoardController(final LeaderBoardService leaderBoardService) {
	 * this.leaderBoardService = leaderBoardService; }
	 */

	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard() {
		return leaderBoardService.getCurrentLeaderBoard();
	}

}
