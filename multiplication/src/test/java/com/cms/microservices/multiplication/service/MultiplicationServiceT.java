package com.cms.microservices.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.microservices.multiplication.model.Multiplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceT {

	@MockBean
	RandomGeneratorService randomGeneratorService;

	@Autowired
	MultiplicationService multiplicationService;

	public void createRandomMultiplicationTest() {

		BDDMockito.given(randomGeneratorService.getRandomFactor()).willReturn(10, 20);

		Multiplication multiplication = multiplicationService.createRandomMultiplication();

		assertThat(multiplication.getFactorA()).isEqualTo(10);
		assertThat(multiplication.getFactorB()).isEqualTo(20);
		//assertThat(multiplication.getResult()).isEqualTo(200);

	}

}
