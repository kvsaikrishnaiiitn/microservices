package com.cms.microservices.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.microservices.gamification.model.BadgeCard;

public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {

	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);

}
