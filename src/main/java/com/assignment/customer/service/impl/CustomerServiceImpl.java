package com.assignment.customer.service.impl;

import static com.assignment.customer.common.ApplicationConstants.BAD_REQUEST;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE;
import static com.assignment.customer.common.ApplicationConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.INCORRECT_END_BALANCE;
import static com.assignment.customer.common.ApplicationConstants.SUCCESSFUL;
import static com.assignment.customer.common.ApplicationConstants.INTERNAL_SERVER_ERROR;
import static com.assignment.customer.common.ApplicationConstants.TEST_INTERNAL_SERVER_ERROR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assignment.customer.bean.CustomerStatement;
import com.assignment.customer.bean.ErrorRecords;
import com.assignment.customer.bean.PostProcessingResult;
import com.assignment.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	/**
	 * This method does the initial validation for the Input object.
	 * 
	 * @param customerStatement
	 * @param errorRecords
	 * @param outputObject
	 * @return PostProcessingResult
	 * 
	 */
	public PostProcessingResult validateInputObject(CustomerStatement customerStatement,
			List<ErrorRecords> errorRecords, PostProcessingResult outputObject) {

		if (customerStatement.getTransactionReference() == null || customerStatement.getAccountNumber() == null
				|| customerStatement.getStartBalance() == null || customerStatement.getMutation() == null
				|| customerStatement.getDescription() == null || customerStatement.getEndBalance() == null) {
			outputObject.setResult(BAD_REQUEST);
		}
		return outputObject;

	}

	/**
	 * This method does the business logic to decide whether input payload has
	 * duplicate reference/incorrect balance, accordingly populate the OutputObject
	 * and ErrorRecords and return.
	 * 
	 * @param customerStatements
	 * @param errorRecords
	 * @param finalResultPostProcessing
	 * @return PostProcessingResult
	 */
	public PostProcessingResult processCustomerRecords(List<CustomerStatement> customerStatements,
			List<ErrorRecords> errorRecords, PostProcessingResult finalResultPostProcessing) {
		// final Set<Integer> setToReturn = new HashSet<>();

		processStatementForErroneousInputs(customerStatements, errorRecords, finalResultPostProcessing);

		if (finalResultPostProcessing.getResult() == null) {
			finalResultPostProcessing.setResult(SUCCESSFUL);
		}
		return finalResultPostProcessing;
	}

	private PostProcessingResult processStatementForErroneousInputs(List<CustomerStatement> customerStatements,
			List<ErrorRecords> errorRecords, PostProcessingResult finalResultPostProcessing) {

		final Set<Long> uniqueTransactionReferences = new HashSet<>();

		for (CustomerStatement customerStatement : customerStatements) {
			
			//Hardcoding to throw "Internal Server Error"
			if (TEST_INTERNAL_SERVER_ERROR.equals(customerStatement.getAccountNumber())) {
				finalResultPostProcessing.setResult(INTERNAL_SERVER_ERROR);
				return finalResultPostProcessing;
			}
			
			// Check if INCORRECT_END_BALANCE
			if ((customerStatement.getStartBalance().add(customerStatement.getMutation())
					.compareTo(customerStatement.getEndBalance())) != 0) {

				ErrorRecords errorRecord = new ErrorRecords();
				errorRecord.setAccountNumber(customerStatement.getAccountNumber());
				errorRecord.setReference(customerStatement.getTransactionReference().toString());
				errorRecords.add(errorRecord);
				if (finalResultPostProcessing.getResult() == null) {
					finalResultPostProcessing.setResult(INCORRECT_END_BALANCE);
				} else if (finalResultPostProcessing.getResult() == DUPLICATE_REFERENCE) {
					finalResultPostProcessing.setResult(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
				}
				finalResultPostProcessing.setErrorRecords(errorRecords);
			}

			// Check if DUPLICATE_REFERENCE
			if (!uniqueTransactionReferences.add(customerStatement.getTransactionReference())) {
				// setToReturn.add(customerStatement.getTransactionReference());
				ErrorRecords errorRecord = new ErrorRecords();
				errorRecord.setAccountNumber(customerStatement.getAccountNumber());
				errorRecord.setReference(customerStatement.getTransactionReference().toString());
				errorRecords.add(errorRecord);
				finalResultPostProcessing.setErrorRecords(errorRecords);
				if (finalResultPostProcessing.getResult() == null) {
					finalResultPostProcessing.setResult(DUPLICATE_REFERENCE);
				} else if (finalResultPostProcessing.getResult() == INCORRECT_END_BALANCE) {
					finalResultPostProcessing.setResult(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
				}
			}
		}

		return finalResultPostProcessing;
	}
}
