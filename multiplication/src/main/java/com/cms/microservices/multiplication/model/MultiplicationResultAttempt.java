package com.cms.microservices.multiplication.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class MultiplicationResultAttempt {

	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;

}
