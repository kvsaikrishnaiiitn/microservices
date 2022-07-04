package com.cms.microservices.multiplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public final class Multiplication {

	private final int factorA;
	private final int factorB;

	public Multiplication() {
		this(0, 0);
	}
}
