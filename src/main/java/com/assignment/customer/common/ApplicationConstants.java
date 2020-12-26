package com.assignment.customer.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Payel
 *
 */

public final class ApplicationConstants {
	
	private ApplicationConstants() {
        // restrict instantiation
	}
	
	/*@Value("${response.successful}")
    public static String SUCCESSFUL;
	
	@Value("${response.duplicate_reference}")
	public static String DUPLICATE_REFERENCE;
	
	@Value("${response.incorrect_end_balance}")
    public static String INCORRECT_END_BALANCE;
	
	@Value("${response.duplicate_reference_incorrect_end_balance}")
    public static String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
	
	@Value("${response.bad_request}")
    public static String BAD_REQUEST;
	
	@Value("${response.internal_server_error}")
    public static String INTERNAL_SERVER_ERROR;*/
	
	public static final String SUCCESSFUL = "SUCCESSFUL";
    public static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
    public static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";
    public static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String TEST_INTERNAL_SERVER_ERROR = "TEST_INTERNAL_SERVER_ERROR";

}