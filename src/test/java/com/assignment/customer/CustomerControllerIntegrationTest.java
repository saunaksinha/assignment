package com.assignment.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.customer.data.CustomerStatement;
import com.assignment.customer.data.OutputObject;


/**
 * @author Payel
 *
 */
@SuppressWarnings("unchecked")
@RunWith(SpringRunner.class)
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
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement, headers);

		ResponseEntity<OutputObject> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, OutputObject.class);
		assertEquals("SUCCESSFUL", response.getBody().getResult());
	}
	
	@Test
	public void testDuplicateReferenceFLow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(duplicateReference, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement, headers);

		ResponseEntity<OutputObject> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, OutputObject.class);
		assertEquals("DUPLICATE_REFERENCE", response.getBody().getResult());
	}
	
	@Test
	public void testIncorrectEndBalanceFLow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(incorrectEndBalanceJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement, headers);

		ResponseEntity<OutputObject> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, OutputObject.class);
		assertEquals("INCORRECT_END_BALANCE", response.getBody().getResult());
	}
	
	@Test
	public void testDuplicateReferenceIncorrectEndBalanceFlow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(duplicateReferenceIncorrectEndBalanceJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement, headers);

		ResponseEntity<OutputObject> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, OutputObject.class);
		assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", response.getBody().getResult());
	}
	
	@Test
	public void testBadRequestFlow() {
		List<CustomerStatement> customerStatement = null;
		try {
			customerStatement = super.mapFromJson(badRequestJson, ArrayList.class);
		} catch (JsonParseException | IOException e) {
			log.error(e.getMessage());
		}
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<List<CustomerStatement>>(customerStatement, headers);

		ResponseEntity<OutputObject> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, OutputObject.class);
		assertEquals("BAD_REQUEST", response.getBody().getResult());
	}
	
	
}	
	
