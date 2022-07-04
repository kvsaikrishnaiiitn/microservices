package com.cms.microservices.multiplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;

public interface MultiplicationResultAttemptRepository extends JpaRepository<MultiplicationResultAttempt, Long> {

	List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);

}
