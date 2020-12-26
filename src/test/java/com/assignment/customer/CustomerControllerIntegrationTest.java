package com.assignment.customer;

import static com.assignment.customer.common.ApplicationConstants.BAD_REQUEST;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.INTERNAL_SERVER_ERROR;
import static com.assignment.customer.common.ApplicationConstants.SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.assignment.customer.bean.CustomerStatement;
import com.assignment.customer.bean.PostProcessingResult;

/**
 * Integration Test Class for Customer Statement Processing Application
 * @author Payel
 *
 */
@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerStatementProcessorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest extends CustomerStatementProcessorTests {

	Logger log = LoggerFactory.getLogger(CustomerControllerIntegrationTest.class);

	String successfulJson = "[{\n" + "	\"transactionReference\": \"1234\",\n" + "	\"accountNumber\": \"abc\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}]";

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
	
	String internalServerErrorJson = "[\n" + "	{\n" + "    \"transactionReference\": 121,	\n"
			+ "	\"accountNumber\": \"abc\",\n" + "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n"
			+ "	\"description\": \"you are here\",\n" + "	\"endBalance\": 111.01\n" + "	},\n" + "	{\n"
			+ "	\"transactionReference\": 122,\n" + "	\"accountNumber\": \"TEST_INTERNAL_SERVER_ERROR\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}]";

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void testSuccessfullFLow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(successfulJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(SUCCESSFUL, response.getBody().getResult());
	}

	@Test
	public void testDuplicateReferenceFLow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(duplicateReference, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(DUPLICATE_REFERENCE, response.getBody().getResult());
	}

	@Test
	public void testIncorrectEndBalanceFLow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(incorrectEndBalanceJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(INCORRECT_END_BALANCE, response.getBody().getResult());
	}

	@Test
	public void testDuplicateReferenceIncorrectEndBalanceFlow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(duplicateReferenceIncorrectEndBalanceJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, response.getBody().getResult());
	}

	@Test
	public void testBadRequestFlow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(badRequestJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(BAD_REQUEST, response.getBody().getResult());
	}
	
	@Test
	public void testInternalServerErrorFlow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(internalServerErrorJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement,
				headers);

		ResponseEntity<PostProcessingResult> response = restTemplate.exchange(createURLWithPort("/customers/process-statement"),
				HttpMethod.POST, entity, PostProcessingResult.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(INTERNAL_SERVER_ERROR, response.getBody().getResult());
	}

}
