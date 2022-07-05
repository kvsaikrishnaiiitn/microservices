package com.cms.microservices.gamification.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Event received when a multiplication has been solved in the system. Provides
 * some context information about the multiplication.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;

	MultiplicationSolvedEvent() {
		this(0L, 0L, false);
	}

}