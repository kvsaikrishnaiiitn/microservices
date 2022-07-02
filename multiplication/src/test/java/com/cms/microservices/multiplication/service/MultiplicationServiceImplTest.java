package com.cms.microservices.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;
import com.cms.microservices.multiplication.model.User;
import com.cms.microservices.multiplication.service.impl.MultiplicationServiceImpl;

public class MultiplicationServiceImplTest {

	@Autowired
	MultiplicationService multiplicationService;

	@Before
	public void setUp() {
		multiplicationService = new MultiplicationServiceImpl();
	}

	@Test
	public void checkCorrectAttemptTest() {

		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("cms");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		assertThat(attemptResult).isTrue();

	}

	@Test
	public void checkWrongAttemptTest() {

		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("cms");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		assertThat(attemptResult).isFalse();

	}

}
