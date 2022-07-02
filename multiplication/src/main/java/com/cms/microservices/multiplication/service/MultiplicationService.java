package com.cms.microservices.multiplication.service;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;

public interface MultiplicationService {

	Multiplication createRandomMultiplication();

	boolean checkAttempt(final MultiplicationResultAttempt attempt);

}
