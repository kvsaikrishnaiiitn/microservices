package com.cms.microservices.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cms.microservices.multiplication.event.EventDispatcher;
import com.cms.microservices.multiplication.event.MultiplicationSolvedEvent;
import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;
import com.cms.microservices.multiplication.model.User;
import com.cms.microservices.multiplication.repository.MultiplicationRepository;
import com.cms.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.cms.microservices.multiplication.repository.UserRepository;
import com.cms.microservices.multiplication.service.impl.MultiplicationServiceImpl;

public class MultiplicationServiceImplTest {

	@InjectMocks
	MultiplicationServiceImpl multiplicationServiceImpl;

	@Mock
	RandomGeneratorService randomGeneratorService;

	@Mock
	MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	MultiplicationRepository multiplicationRepository;

	@Mock
	EventDispatcher eventDispatcher;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void checkCorrectAttemptTest() {

		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("cms");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);

		MultiplicationSolvedEvent multiplicationSolvedEvent = new MultiplicationSolvedEvent(attempt.getId(),
				attempt.getUser().getId(), true);

		// given
		BDDMockito.given(userRepository.findByAlias("cms")).willReturn(Optional.empty());
		BDDMockito.given(multiplicationRepository.findByFactorAAndFactorB(50, 60)).willReturn(Optional.empty());

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// then
		assertThat(attemptResult).isTrue();
		verify(multiplicationResultAttemptRepository).save(verifiedAttempt);
		verify(eventDispatcher).send(Mockito.eq(multiplicationSolvedEvent));

	}

	@Test
	public void checkWrongAttemptTest() {

		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("cms");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

		// given
		BDDMockito.given(userRepository.findByAlias("cms")).willReturn(Optional.empty());

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// then
		assertThat(attemptResult).isFalse();
		verify(multiplicationResultAttemptRepository).save(attempt);

	}

	@Test
	public void retrieveStatsTest() {

		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
		List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
		BDDMockito.given(userRepository.findByAlias("cms")).willReturn(Optional.empty());
		BDDMockito.given(multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc("cms"))
				.willReturn(latestAttempts);

		// when
		List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("cms");

		// then
		assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
	}

}
