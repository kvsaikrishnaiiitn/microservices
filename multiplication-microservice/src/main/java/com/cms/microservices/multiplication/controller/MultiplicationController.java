package com.cms.microservices.multiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.service.MultiplicationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/multiplication")
@Slf4j
public class MultiplicationController {

	@Autowired
	private MultiplicationService multiplicationService;

	@Value("${server.port}")
	int serverPort;

	@GetMapping("/random")
	public Multiplication getRandomMultiplication() {
		log.info("Generating a random multiplication from server @ {}", serverPort);
		return multiplicationService.createRandomMultiplication();
	}

}
