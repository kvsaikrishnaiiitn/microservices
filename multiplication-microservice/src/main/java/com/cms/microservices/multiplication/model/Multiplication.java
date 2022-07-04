package com.cms.microservices.multiplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
public final class Multiplication {
	
	@Id
	@GeneratedValue
	@Column(name = "multiplication_id")
	private Long id;

	private final int factorA;
	private final int factorB;

	//for JSON/JPA - otherwise getting 400 error
	public Multiplication() {
		this(0, 0);
	}
}
