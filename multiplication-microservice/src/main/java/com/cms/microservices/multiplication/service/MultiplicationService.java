package com.cms.microservices.multiplication.service;

import java.util.List;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;

public interface MultiplicationService {

	Multiplication createRandomMultiplication();

	boolean checkAttempt(final MultiplicationResultAttempt multiplicationResultAttempt);

	List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

	MultiplicationResultAttempt getResultById(final Long resultId);

}
