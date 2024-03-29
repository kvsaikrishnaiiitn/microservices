package com.cms.microservices.gamification.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cms.microservices.gamification.client.dto.MultiplicationResultAttempt;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * This implementation of MultiplicationResultAttemptClient interface connects
 * to the Multiplication microservice via REST.
 */
@Component
class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${multiplicationHost}")
	private String multiplicationHost;

	/*
	 * @Autowired public MultiplicationResultAttemptClientImpl(final RestTemplate
	 * restTemplate,
	 * 
	 * @Value("${multiplicationHost}") final String multiplicationHost) {
	 * this.restTemplate = restTemplate; this.multiplicationHost =
	 * multiplicationHost; }
	 */

	@HystrixCommand(fallbackMethod = "defaultResult")
	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId(
			final Long multiplicationResultAttemptId) {
		return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationResultAttemptId,
				MultiplicationResultAttempt.class);
	}
	
	 private MultiplicationResultAttempt defaultResult(final Long multiplicationResultAttemptId) {
	        return new MultiplicationResultAttempt("fakeAlias",
	                10, 10, 100, true);
	    }
}