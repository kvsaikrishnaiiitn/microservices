package com.cms.microservices.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cms.microservices.gamification.model.LeaderBoardRow;
import com.cms.microservices.gamification.model.ScoreCard;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

	@Query("SELECT SUM(s.score) FROM com.cms.microservices.gamification.model.ScoreCard s WHERE s.userId =:userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);

	@Query("SELECT NEW com.cms.microservices.gamification.model.LeaderBoardRow(s.userId, SUM(s.score)) FROM com.cms.microservices.gamification.model.ScoreCard s"
			+ " GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();

	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
	
	ScoreCard findByAttemptId(final Long attemptId);

}
