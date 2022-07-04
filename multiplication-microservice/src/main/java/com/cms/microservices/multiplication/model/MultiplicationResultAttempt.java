package com.cms.microservices.multiplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private final User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "multiplication_id")
	private final Multiplication multiplication;
	
	private final int resultAttempt;
	private final boolean correct;

	// for JSON/JPA - otherwise getting 400 error
	public MultiplicationResultAttempt() {
		this.user = null;
		this.multiplication = null;
		resultAttempt = -1;
		correct = false;
	}

}
