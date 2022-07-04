package com.cms.microservices.multiplication.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.cms.microservices.multiplication.service.RandomGeneratorService;

@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {

	final static int MINIMUM_FACTOR = 11;
	final static int MAXIMUM_FACTOR = 99;

	@Override
	public int getRandomFactor() {
		return new Random().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR;
	}

	/*
	 * @Override public int getRandomFactor() { return 0; }
	 */

}
