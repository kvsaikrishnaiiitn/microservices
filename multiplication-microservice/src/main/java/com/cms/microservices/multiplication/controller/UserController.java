package com.cms.microservices.multiplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.microservices.multiplication.model.User;
import com.cms.microservices.multiplication.repository.UserRepository;

/**
 * @author moises.macero
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") final Long userId){
        return userRepository.findOne(userId);
    }
}