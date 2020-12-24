package com.assignment.customer.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customer.data.CustomerStatement;
import com.assignment.customer.data.ErrorRecord;
import com.assignment.customer.data.OutputObject;

@RequestMapping("/customer")
@RestController
public class CustomerController {

	@RequestMapping("/process")
	public @ResponseBody OutputObject processCustomerRecord (@RequestBody List<CustomerStatement> customerStatements) {
		
		try {
			System.out.println("processCustomerRecord");
			List <ErrorRecord> errorRecords = new ArrayList<ErrorRecord>();
			OutputObject outputObject = new OutputObject();
			Iterator <CustomerStatement> customerStatementList = customerStatements.iterator();
			while(customerStatementList.hasNext()) {
				outputObject = validateInputObject(customerStatementList.next(),errorRecords,outputObject);
			}
			
			if (outputObject.getResult() == "BAD_REQUEST") {
				return outputObject;
			}
			
			outputObject = processCustomerRecords(customerStatements, errorRecords, outputObject);
			return outputObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			OutputObject outputObjectException = new OutputObject();
			outputObjectException.setResult("INTERNAL_SERVER_ERROR");
			return outputObjectException;
		}
	}
	
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

	private OutputObject validateInputObject(CustomerStatement customerStatement, List<ErrorRecord> errorRecords, OutputObject outputObject) {
		
		if (customerStatement.getTransactionReference() == null || customerStatement.getAccountNumber() == null || customerStatement.getStartBalance() == null
				|| customerStatement.getMutation() == null || customerStatement.getDescription() == null || customerStatement.getEndBalance() == null) {
			outputObject.setResult("BAD_REQUEST");
		}
		return outputObject;
		
	}
	
	/*private OutputObject checkDuplicateReference(CustomerStatement customerStatement, List<ErrorRecord> errorRecords,
			OutputObject outputObject) {
		
		if (customerStatement.getTransactionReference() == 123) {
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
		return outputObject;
	}

	private OutputObject checkIncorrectBalance (CustomerStatement customerStatement, List<ErrorRecord> errorRecords, OutputObject outputObject) {
		if ((customerStatement.getStartBalance().add(customerStatement.getMutation())
				.compareTo(customerStatement.getEndBalance())) == 0) {
					System.out.println("Its a Match");
				} else {
					System.out.println("Not a Match");
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
		return outputObject;
	}*/
	
	/*public Set<Integer> findDuplicates(List<Integer> listContainingDuplicates)
	{ 
	  final Set<Integer> setToReturn = new HashSet<>(); 
	  final Set<Integer> set1 = new HashSet<>();

	  for (Integer yourInt : listContainingDuplicates)
	  {
	   if (!set1.add(yourInt))
	   {
	    setToReturn.add(yourInt);
	   }
	  }
	  return setToReturn;
	}*/
	
}
