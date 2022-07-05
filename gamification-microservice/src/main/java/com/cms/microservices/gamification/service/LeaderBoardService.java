package com.cms.microservices.gamification.service;

import java.util.List;

import com.cms.microservices.gamification.model.LeaderBoardRow;

public interface LeaderBoardService {

	List<LeaderBoardRow> getCurrentLeaderBoard();

}
