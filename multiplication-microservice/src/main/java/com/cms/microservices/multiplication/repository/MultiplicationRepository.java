package com.cms.microservices.multiplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.microservices.multiplication.model.Multiplication;

public interface MultiplicationRepository extends JpaRepository<Multiplication, Long> {

	Optional<Multiplication> findByFactorAAndFactorB(final int factorA, final int factorB);

}
