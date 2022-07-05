package com.cms.microservices.multiplication.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cms.microservices.multiplication.event.EventDispatcher;
import com.cms.microservices.multiplication.event.MultiplicationSolvedEvent;
import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;
import com.cms.microservices.multiplication.model.User;
import com.cms.microservices.multiplication.repository.MultiplicationRepository;
import com.cms.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.cms.microservices.multiplication.repository.UserRepository;
import com.cms.microservices.multiplication.service.MultiplicationService;
import com.cms.microservices.multiplication.service.RandomGeneratorService;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	@Autowired
	RandomGeneratorService randomGeneratorService;

	@Autowired
	MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MultiplicationRepository multiplicationRepository;

	@Autowired
	EventDispatcher eventDispatcher;

	@Override
	public Multiplication createRandomMultiplication() {
		return new Multiplication(randomGeneratorService.getRandomFactor(), randomGeneratorService.getRandomFactor());
	}

	@Transactional
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt multiplicationResultAttempt) {
		Assert.isTrue(!multiplicationResultAttempt.isCorrect(), "You can't send an attempt marked as correct.");

		Optional<User> user = userRepository.findByAlias(multiplicationResultAttempt.getUser().getAlias());
		Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(
				multiplicationResultAttempt.getMultiplication().getFactorA(),
				multiplicationResultAttempt.getMultiplication().getFactorB());

		boolean isCorrect = multiplicationResultAttempt.getResultAttempt() == multiplicationResultAttempt
				.getMultiplication().getFactorA() * multiplicationResultAttempt.getMultiplication().getFactorB();

		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
				user.orElse(multiplicationResultAttempt.getUser()),
				multiplication.orElse(multiplicationResultAttempt.getMultiplication()),
				multiplicationResultAttempt.getResultAttempt(), isCorrect);

		multiplicationResultAttemptRepository.save(checkedAttempt);

		eventDispatcher.send(
				new MultiplicationSolvedEvent(checkedAttempt.getId(), checkedAttempt.getUser().getId(), isCorrect));

		return isCorrect;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
		return multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}

	@Override
	public MultiplicationResultAttempt getResultById(final Long resultId) {
		return multiplicationResultAttemptRepository.findById(resultId).orElseThrow(
				() -> new RuntimeException("Unable to find MultiplicationResultAttempt with id:" + resultId));
	}

}
