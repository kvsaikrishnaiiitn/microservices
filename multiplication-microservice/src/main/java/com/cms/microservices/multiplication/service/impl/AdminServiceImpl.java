package com.cms.microservices.multiplication.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.cms.microservices.multiplication.repository.MultiplicationRepository;
import com.cms.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.cms.microservices.multiplication.repository.UserRepository;
import com.cms.microservices.multiplication.service.AdminService;

/**
 * @author moises.macero
 */
@Profile("test")
@Service
public class AdminServiceImpl implements AdminService {

    private MultiplicationRepository multiplicationRepository;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;

    public AdminServiceImpl(final MultiplicationRepository multiplicationRepository,
                            final UserRepository userRepository,
                            final MultiplicationResultAttemptRepository attemptRepository) {
        this.multiplicationRepository = multiplicationRepository;
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    @Override
    public void deleteDatabaseContents() {
        attemptRepository.deleteAll();
        multiplicationRepository.deleteAll();
        userRepository.deleteAll();
    }
}