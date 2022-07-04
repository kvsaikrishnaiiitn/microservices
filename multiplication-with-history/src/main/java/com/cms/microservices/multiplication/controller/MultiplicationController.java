package com.cms.microservices.multiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/multiplication")
public class MultiplicationController {

	@Autowired
	private MultiplicationService multiplicationService;

	@GetMapping("/random")
	public Multiplication getRandomMultiplication() {
		return multiplicationService.createRandomMultiplication();
	}

}
