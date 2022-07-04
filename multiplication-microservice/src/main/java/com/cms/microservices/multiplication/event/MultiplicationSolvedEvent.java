package com.cms.microservices.multiplication.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5447330420524371380L;
	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
}
