package com.cms.microservices.multiplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.microservices.multiplication.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByAlias(final String alias);

}
