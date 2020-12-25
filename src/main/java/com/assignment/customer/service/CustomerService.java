package com.assignment.customer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.assignment.customer.data.CustomerStatement;
import com.assignment.customer.data.ErrorRecord;
import com.assignment.customer.data.OutputObject;

@Service
public class CustomerService {

	/*
	 * This method does the initial validation for the Input object. 
	 */
	public OutputObject validateInputObject(CustomerStatement customerStatement, List<ErrorRecord> errorRecords, OutputObject outputObject) {
		
		if (customerStatement.getTransactionReference() == null || customerStatement.getAccountNumber() == null || customerStatement.getStartBalance() == null
				|| customerStatement.getMutation() == null || customerStatement.getDescription() == null || customerStatement.getEndBalance() == null) {
			outputObject.setResult("BAD_REQUEST");
		}
		return outputObject;
		
	}
	
	/*
	 * This method does the business logic to decide whether input payload has duplicate reference/incorrect balance,
	 * accordingly populate the OutputObject and ErrorRecords and return. 
	 */
	public OutputObject processCustomerRecords(List<CustomerStatement> customerStatements, List<ErrorRecord> errorRecords,
			OutputObject outputObject)
	{ 
	  final Set<Integer> setToReturn = new HashSet<>(); 
	  final Set<Integer> set1 = new HashSet<>();

	  for (CustomerStatement customerStatement : customerStatements)
	  {
	   
	   //Check if INCORRECT_END_BALANCE	  
	   if ((customerStatement.getStartBalance().add(customerStatement.getMutation())
					.compareTo(customerStatement.getEndBalance())) != 0) {
		   
		   ErrorRecord errorRecord = new ErrorRecord();
			errorRecord.setAccountNumber(customerStatement.getAccountNumber());
			errorRecord.setReference(customerStatement.getTransactionReference().toString());
			errorRecords.add(errorRecord);
			if (outputObject.getResult() == null) {
			outputObject.setResult("INCORRECT_END_BALANCE");
			} else if (outputObject.getResult() == "DUPLICATE_REFERENCE") {
				outputObject.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE");
			}
			outputObject.setErrorRecords(errorRecords);
	   }
		
	  //Check if DUPLICATE_REFERENCE
		if (!set1.add(customerStatement.getTransactionReference())) {
	    setToReturn.add(customerStatement.getTransactionReference());
	    ErrorRecord errorRecord = new ErrorRecord();
		errorRecord.setAccountNumber(customerStatement.getAccountNumber());
		errorRecord.setReference(customerStatement.getTransactionReference().toString());
		errorRecords.add(errorRecord);
		outputObject.setErrorRecords(errorRecords);
		if (outputObject.getResult() == null) {
			outputObject.setResult("DUPLICATE_REFERENCE");
		}
		else if (outputObject.getResult() == "INCORRECT_END_BALANCE") {
			outputObject.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE");
		}
	   }
	  }
	  
	  if (outputObject.getResult() == null) {
		  outputObject.setResult("SUCCESSFUL");
	  }
	  return outputObject;
	}
	
}
