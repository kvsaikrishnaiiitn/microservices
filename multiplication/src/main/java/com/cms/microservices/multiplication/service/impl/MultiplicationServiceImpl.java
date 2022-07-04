package com.cms.microservices.multiplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;
import com.cms.microservices.multiplication.service.MultiplicationService;
import com.cms.microservices.multiplication.service.RandomGeneratorService;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	@Autowired
	RandomGeneratorService randomGeneratorService;

	@Override
	public Multiplication createRandomMultiplication() {
		return new Multiplication(randomGeneratorService.getRandomFactor(), randomGeneratorService.getRandomFactor());
	}

	@Override
	public boolean checkAttempt(MultiplicationResultAttempt multiplicationResultAttempt) {
		return multiplicationResultAttempt.getResultAttempt() == multiplicationResultAttempt.getMultiplication().getFactorA()
				* multiplicationResultAttempt.getMultiplication().getFactorB();
	}

}
