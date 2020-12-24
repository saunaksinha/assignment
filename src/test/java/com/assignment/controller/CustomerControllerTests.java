package com.assignment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(value = CustomerController.class)
@SpringBootTest
class CustomerControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	String successfulJson = "[{\n"
			+ "	\"transactionReference\": \"1234\",\n"
			+ "	\"accountNumber\": \"abc\",\n"
			+ "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n"
			+ "	}]";
	String incorrectEndBalanceJson = "";
	String duplicateReferenceCorrectEndBalanceJson = "";
	String correctReferenceIncorrectEndBalanceJson = "";
	String duplicateReferenceIncorrectEndBalanceJson = "";
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	public void checkIncorrectEndBalance() throws Exception {
		String uri = "/customer/process";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/process")
				.accept(MediaType.APPLICATION_JSON).content(successfulJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
}