package com.assignment.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.assignment.customer.common.ApplicationConstants.SUCCESSFUL;
import static com.assignment.customer.common.ApplicationConstants.BAD_REQUEST;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.INTERNAL_SERVER_ERROR;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.customer.bean.PostProcessingResult;

/**
 * JUnit test cases for unit testing
 * @author Payel
 *
 */

public class CustomerControllerTests extends CustomerStatementProcessorTests {
	@Autowired
	private MockMvc mockMvc;

	String successfulJson = "[{\n" + "	\"transactionReference\": \"1234\",\n" + "	\"accountNumber\": \"abc\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}]";

	String incorrectEndBalanceJson = "[\n" + "	{\n" + "	\"transactionReference\": 121,\n"
			+ "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 122,\n" + "	\"accountNumber\": \"abc1\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	},\n" + "	{\n" + "	\"transactionReference\": 123,\n" + "	\"accountNumber\": \"abc2\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n" + "	\"transactionReference\": 112,\n"
			+ "	\"accountNumber\": \"abc4\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 111,\n" + "	\"accountNumber\": \"abc3\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	}\n" + "]";
	String duplicateReference = "[\n" + "	{\n" + "	\"transactionReference\": 121,\n"
			+ "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 121,\n" + "	\"accountNumber\": \"abc1\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	},\n" + "	{\n" + "	\"transactionReference\": 121,\n" + "	\"accountNumber\": \"abc2\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n" + "	\"transactionReference\": 112,\n"
			+ "	\"accountNumber\": \"abc4\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 111,\n" + "	\"accountNumber\": \"abc3\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n"
			+ "	}\n" + "]";

	String badRequestJson = "[\n" + "	{\n" + "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	},\n" + "	{\n" + "	\"transactionReference\": 121,\n" + "	\"accountNumber\": \"abc1\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n" + "	\"transactionReference\": 123,\n"
			+ "	\"accountNumber\": \"abc2\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 112,\n" + "	\"accountNumber\": \"abc4\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n"
			+ "	},\n" + "	{\n" + "	\"transactionReference\": 111,\n" + "	\"accountNumber\": \"abc3\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}\n" + "]";
	String duplicateReferenceIncorrectEndBalanceJson = "[\n" + "	{\n" + "	\"transactionReference\": 121,\n"
			+ "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 121,\n" + "	\"accountNumber\": \"abc1\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	},\n" + "	{\n" + "	\"transactionReference\": 123,\n" + "	\"accountNumber\": \"abc2\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n" + "	\"transactionReference\": 112,\n"
			+ "	\"accountNumber\": \"abc4\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.00,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.00\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 111,\n" + "	\"accountNumber\": \"abc3\",\n" + "	\"startBalance\": 100.00,\n"
			+ "	\"mutation\": 11.00,\n" + "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n"
			+ "	}\n" + "]";
	
	String internalServerErrorJson = "[\n" + "	{\n" + "    \"transactionReference\": 121,	\n"
			+ "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 122,\n" + "	\"accountNumber\": \"TEST_INTERNAL_SERVER_ERROR\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}]";

	/**
	 * Method to run the test case for a valid payload
	 */
	@Test
	public void testSuccessfullFLow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(successfulJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(SUCCESSFUL, response.getResult());
	}

	/**
	 * Method to run the test case for a payload containing duplicate transaction reference numbers
	 */
	@Test
	public void testDuplicateReferenceFLow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(duplicateReference)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(DUPLICATE_REFERENCE, response.getResult());
	}

	/**
	 * Method to run the test case for a payload containing incorrect value as end balance for an individual transaction reference
	 */
	@Test
	public void testIncorrectEndBalanceFLow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(incorrectEndBalanceJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(INCORRECT_END_BALANCE, response.getResult());
	}

	/**
	 * Method to run the test case for a payload containing both duplicate transaction reference numbers and incorrect data as end balance
	 */
	@Test
	public void testDuplicateReferenceIncorrectEndBalanceFlow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(duplicateReferenceIncorrectEndBalanceJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, response.getResult());
	}

	/**
	 * Method to run the test case for a payload with incorrect JSON request format
	 */
	@Test
	public void testBadRequestFlow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(badRequestJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(BAD_REQUEST, response.getResult());
	}
	
	/**
	 * Method to run the test case for a payload causing Internal Server Error, 
	 * in our case it is a specific value of account number derived from properties file that would account for this test case
	 */
	@Test
	public void testInternalServerErrorFlow() throws Exception {
		String uri = "/customers/process-statement";
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(internalServerErrorJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		PostProcessingResult response = super.mapFromJson(content, PostProcessingResult.class);
		assertEquals(INTERNAL_SERVER_ERROR, response.getResult());
	}

}