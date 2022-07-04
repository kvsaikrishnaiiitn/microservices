package com.cms.microservices.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.microservices.multiplication.model.Multiplication;
import com.cms.microservices.multiplication.model.MultiplicationResultAttempt;
import com.cms.microservices.multiplication.model.User;
import com.cms.microservices.multiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	MockMvc mvc;

	private JacksonTester<MultiplicationResultAttempt> jsonResult;

	private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}

	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}

	private void genericParameterizedTest(final boolean correct) throws Exception {

		// given
		BDDMockito.given(multiplicationService.checkAttempt(Mockito.any(MultiplicationResultAttempt.class)))
				.willReturn(correct);

		Multiplication multiplication = new Multiplication(10, 20);
		User user = new User("cms");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 200, correct);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonResult.write(attempt).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(
				response.getContentAsString())
						.isEqualTo(
								jsonResult
										.write(new MultiplicationResultAttempt(attempt.getUser(),
												attempt.getMultiplication(), attempt.getResultAttempt(), correct))
										.getJson());

	}

	@Test
	public void getUserStats() throws Exception {

		// given
		User user = new User("cms");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt multiplicationResultAttempt = new MultiplicationResultAttempt(user, multiplication,
				3500, false);
		List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(multiplicationResultAttempt,
				multiplicationResultAttempt);
		BDDMockito.given(multiplicationService.getStatsForUser("cms")).willReturn(recentAttempts);

		// when
		MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "cms")).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());

	}
}
