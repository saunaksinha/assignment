# assignment

Customer Statement Processor Service

This is a Spring Boot Application implementing a REST API to validate a monthly list of Customer Statement.

About
This application processes customer statements to ensure each transaction reference number is unique in a single payload and the sum total of Starting Balance and Mutation is equal to the Ending Balance (per transaction reference). Since there has been no business requirement of persisting data, thereby there is no database transactions available with this service implementation.

This API is built to address the following error scenarios as part of customer statement payload validation process: 
1. In case a transaction reference number is repetitive in a payload, then the corresponding transaction reference would be marked as duplicate. In the final response JSON, results would be captured as DUPLICATE_REFERENCE and the duplicate transaction reference numbers and account numbers would get added as part of the errorRecords. 
2. Additionally, if the Ending Balance and sum of Starting Balance and mutation do not match(for a particular transaction in the payload) then the transaction would be marked as Incorrect Balance. In the final response JSON, results would be captured as INCORRECT_END_BALANCE and the erroneous transaction reference numbers and account numbers would get added as part of the errorRecords. 
3. In case, both points 1 and 2 is applied for a particular payload file then the output would be marked as both Duplicate Reference and Incorrect Balance. In the final response JSON, results would be captured as DUPLICATE_REFERENCE_INCORRECT_END_BALANCE and the erroneous transaction reference numbers and account numbers would get added as part of the errorRecords. 

The response JSON objects are prepared adhering to the sample JSON provided in the requirement. 

The server.port has also been overridden in the application.properties file to 8090, thereby application will run on port 8090 instead 8080(default). 

This project has Lombok added as dependency , hence same need to be applied to the IDE before running test cases/ build from the IDE. I was using STS and I had to install Lombok jar into STS. 

This application has been built using Spring Boot 2. 
Following is the technology stack that has been used with respect to the application:
Java 11
Spring Boot 2
JUnit 5

Operations
POST : /customers/process-statement - For validating and processing list of Customer Statements

Test Cases: 

I have considered 6 test scenarios for this implementation. Scenario 6 - Internal Server Error , HTTPStatusCode 500 has been achieved by doing some additional logic. If accountNumber in the payload is sent as TEST_INTERNAL_SERVER_ERROR (this value is configuration driven, it can be modified by application.properties file), then application will throw InternalServerError with code 500. 

		| Http Status Code  | Condition                                                         |  Response format |
		|---                |---                                                                |---               |
Scenario 1> 	| 200               |When there are no duplicate reference and correct end balance      | `{"result" : "SUCCESSFUL", "errorRecords" : []}`|
Scenario 2> 	| 200               |When there are duplicate reference and correct balance             |[duplicateReferenceAndcorrectBalance Json](./duplicateReferenceAndcorrectBalance.json)|
Scenario 3>	| 200               |When there are no duplicate reference and In correct balance       |[IncorrectBalance Json](./IncorrectBalance.json)|
Scenario 4>	| 200               |When there are duplicate reference and In correct balance          |[duplicateReferenceAndIncorrectBalance Json](./duplicateReferenceAndIncorrectBalance.json)|
Scenario 5>	| 400               |Error during parsing JSON                                          | `{"result" : "BAD_REQUEST", "errorRecords" : []}`|
Scenario 6>	| 500               |Any other situation                                                |`{"result" : "INTERNAL_SERVER_ERROR","errorRecords" : [] }`|


Payload for Scenario 1> 

[
	{
    "transactionReference": 121,	
	"accountNumber": "SAMPLE_ACCOUNT_1",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 122,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.00
	}
]


Payload for Scenario 2> 

[
	{
    "transactionReference": 121,	
	"accountNumber": "SAMPLE_ACCOUNT_1",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 121,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.00
	}
]

Payload for Scenario 3>

[
	{
    "transactionReference": 121,	
	"accountNumber": "SAMPLE_ACCOUNT_1",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 122,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.01
	}
]

Payload for Scenario 4>

[
	{
    "transactionReference": 121,	
	"accountNumber": "SAMPLE_ACCOUNT_1",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 121,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.01
	}
]

Payload for Scenario 5>

[
	{
	"accountNumber": "SAMPLE_ACCOUNT_1",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 121,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.01
	}
]

Payload for Scenario 6>

[
	{
    "transactionReference": 121,	
	"accountNumber": "TEST_INTERNAL_SERVER_ERROR",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 121,
	"accountNumber": "abc",
	"startBalance": 100.00,
	"mutation": 11.01,
	"description": "you are here",
	"endBalance": 111.01
	},
	{
	"transactionReference": 123,
	"accountNumber": "abc2",
	"startBalance": 100.00,
	"mutation": 11.00,
	"description": "you are here",
	"endBalance": 111.01
	}
]

    
Production Enablement
Extensive test cases have been incorporated which includes both JUnit and Spring Integration.

Installation
The application is build with Spring Boot 2 and would get deployed within the SpringBoot contained Tomcat server. Please build and run the customer-statement-processor-0.0.1-SNAPSHOT.jar. 
Use below command to start locally
java -jar target/customer-statement-processor-0.0.1-SNAPSHOT.jar

Contributor
Payel Saha --- payel.saha85@gmail.com