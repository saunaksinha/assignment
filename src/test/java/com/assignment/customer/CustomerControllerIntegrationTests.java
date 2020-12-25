package com.assignment.customer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerStatementProcessorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTests extends CustomerStatementProcessorTests {

	String successfulJson = "[{\n" + "	\"transactionReference\": \"1234\",\n" + "	\"accountNumber\": \"abc\",\n"
			+ "	\"startBalance\": 100.00,\n" + "	\"mutation\": 11.01,\n" + "	\"description\": \"you are here\",\n"
			+ "	\"endBalance\": 111.01\n" + "	}]";
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	/*@Test
	public void testRetrieveStudentCourse() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/Student1/courses/Course1"),
				HttpMethod.GET, entity, String.class);

		String expected = "{id:Course1,name:Spring,description:10Steps}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}*/

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@Test
	public void addCourse() {
		CustomerStatement customerStatement = null;
		try {
			customerStatement = super.mapFromJson(successfulJson, CustomerStatement.class);
		} catch (JsonParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity<CustomerStatement> entity = new HttpEntity<CustomerStatement>(customerStatement, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/customers/process"),
				HttpMethod.POST, entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		System.out.println("actual"+actual);
		assertTrue(actual.contains("/students/Student1/courses/"));

	}
}	
	
