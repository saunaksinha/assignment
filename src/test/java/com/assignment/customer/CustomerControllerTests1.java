package com.assignment.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.customer.data.OutputObject;

/**
 * @author Cognizant
 *
 */

public class CustomerControllerTests1 extends CustomerStatementProcessorTests {
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
	
	@Test
	public void testSuccessfullFLow() throws Exception {
	String uri = "/customers/process";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(successfulJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	OutputObject response = super.mapFromJson(content, OutputObject.class);
	assertEquals("SUCCESSFUL", response.getResult());
	System.out.println("print-content"+content);
	}
	
	
	@Test
	public void testDuplicateReferenceFLow() throws Exception {
	String uri = "/customers/process";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(duplicateReference)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	OutputObject response = super.mapFromJson(content, OutputObject.class);
	assertEquals("DUPLICATE_REFERENCE", response.getResult());
	System.out.println("print-content"+content);
	}
	
	@Test
	public void testIncorrectEndBalanceFLow() throws Exception {
	String uri = "/customers/process";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(incorrectEndBalanceJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	OutputObject response = super.mapFromJson(content, OutputObject.class);
	assertEquals("INCORRECT_END_BALANCE", response.getResult());
	System.out.println("print-content"+content);
	}
	
	@Test
	public void testDuplicateReferenceIncorrectEndBalanceFlow() throws Exception {
	String uri = "/customers/process";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(duplicateReferenceIncorrectEndBalanceJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	OutputObject response = super.mapFromJson(content, OutputObject.class);
	assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", response.getResult());
	System.out.println("print-content"+content);
	}
	
	@Test
	public void testBadRequestFlow() throws Exception {
	String uri = "/customers/process";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(badRequestJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(400, status);
	String content = mvcResult.getResponse().getContentAsString();
	OutputObject response = super.mapFromJson(content, OutputObject.class);
	assertEquals("BAD_REQUEST", response.getResult());
	System.out.println("print-content"+content);
	}
	
}

	
	
	/*
	@Test
	public void getDefaultResult() throws Exception {
	String uri = "/test-user";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.queryParam("user-name", "nrudra")
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	assertEquals("The test user flow has been "
			+ "encountered an exception, hence return a fallback result..", content);
	}​ ​  

	@Test
	public void testValidUser() throws Exception {​​​​​
		String uri = "/is-valid-user";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.queryParam("user-name", "nrudra")
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	assertEquals("true", content);
	}​ ​ ​ ​ ​

	@Test
	public void testUserByUserName_V1() throws Exception {​​​​​
		String uri = "/get-user-by-username";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.header("X-API-VERSION", "1")
			.queryParam("user-name", "nrudra")
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	UserResponse response = super.mapFromJson(content, UserResponse.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response.getStatus());
	assertTrue(response.getUsers().size() > 0);
	assertEquals("Nayan", response.getUsers().get(0).getfName());
	}​ ​ ​ ​ ​

	@Test
	public void testUserByUserName_V2() throws Exception {​​​​​
		String uri = "/get-user-by-username";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.header("X-API-VERSION", "2")
			.queryParam("user-name", "nrudra")
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	User_V2Response response_V2 = super.mapFromJson(content, User_V2Response.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response_V2.getStatus());
	assertTrue(response_V2.getUsers().size() > 0);
	assertEquals("Rudra", response_V2.getUsers().get(0).getFullName().getlName());
	}​ ​ ​ ​ ​

	@Test
	public void testAllUsers_V1() throws Exception {​​​​​
		String uri = "/get-allusers";
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.queryParam("version", "1")
			.queryParam("user-name", "nrudra")
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	UserResponse response = super.mapFromJson(content, UserResponse.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response.getStatus());
	assertTrue(response.getUsers().size() > 0);
	}​ ​ ​ ​ ​

	@Test
	public void testAllUsers_V2() throws Exception {​​​​​
		String uri = "/get-allusers";
	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	List<String> value1 = new ArrayList<>();
	value1.add("2");
	params.put("version", value1);
	List<String> value2 = new ArrayList<>();
	value2.add("nrudra");
	params.put("user-name", value2);
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			.queryParams(params)
			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	User_V2Response response_V2 = super.mapFromJson(content, User_V2Response.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response_V2.getStatus());
	assertTrue(response_V2.getUsers().size() > 0);
	assertEquals("Rudra", response_V2.getUsers().get(0).getFullName().getlName());
	}​ ​ ​ ​ ​

	@Test
	public void testRegisterUser() throws Exception {​​​​​
		String uri = "/register";
	BaseData baseData = new BaseData(DMSConstant.ENTITY_CREATED_BY, new Date());
	User user = new User(10101, "Admin", "LAdmin", "admin123", "root123", "Underwriter", "XYZ Road", "LA", "US",
			"ad.mn@cognizant.com", "GHR664LK", "7890123456", "16/01/1986", "r62tikl", baseData);
	String inputJson = super.mapToJson(user);
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(201, status);
	String content = mvcResult.getResponse().getContentAsString();
	UserResponse response = super.mapFromJson(content, UserResponse.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response.getStatus());
	assertTrue(response.getUsers().size() > 0);
	}​ ​ ​ ​ ​

	@Test
	public void testLoginUser_Invalid() throws Exception {​​​​​
		String uri = "/login";
	User user = new User();
	user.setUserName("admin123");
	user.setPassword("root123");
	String inputJson = super.mapToJson(user);
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(400, status);
	String content = mvcResult.getResponse().getContentAsString();
	UserResponse response = super.mapFromJson(content, UserResponse.class);
	assertEquals(DMSConstant.STATUS_FAILURE, response.getStatus());
	assertEquals("Invalid User Name, please provide a valid user name or register before login !!",
			response.getErrorMsg());
	}​ ​ ​ ​ ​

	@Test
	public void testLoginUser_Valid() throws Exception {​​​​​
		String uri = "/login";
	User user = new User();
	user.setUserName("nrudra");
	user.setPassword("nr123456");
	String inputJson = super.mapToJson(user);
	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	assertEquals(200, status);
	String content = mvcResult.getResponse().getContentAsString();
	UserResponse response = super.mapFromJson(content, UserResponse.class);
	assertEquals(DMSConstant.STATUS_SUCCESS, response.getStatus());
	assertTrue(response.getUsers().size() > 0);
	}*/