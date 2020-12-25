package com.assignment.customer.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customer.data.CustomerStatement;
import com.assignment.customer.data.ErrorRecord;
import com.assignment.customer.data.OutputObject;
import com.assignment.customer.service.CustomerService;


@RequestMapping("/customers")
@RestController
public class CustomerController {
	
	Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;

	@RequestMapping("/process")
	public ResponseEntity <?> processCustomerRecord (@RequestBody List<CustomerStatement> customerStatements) {
		
		try {
			log.info("#######Enter processCustomerRecord()######",CustomerController.class);
			List <ErrorRecord> errorRecords = new ArrayList<ErrorRecord>();
			OutputObject outputObject = new OutputObject();
			Iterator <CustomerStatement> customerStatementList = customerStatements.iterator();
			while(customerStatementList.hasNext()) {
				outputObject = customerService.validateInputObject(customerStatementList.next(),errorRecords,outputObject);
			}
			
			if (outputObject.getResult() == "BAD_REQUEST") {
				return new ResponseEntity<>(outputObject,HttpStatus.BAD_REQUEST);
			}
			
			customerService.processCustomerRecords(customerStatements, errorRecords, outputObject);
			return new ResponseEntity<>(outputObject,HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			OutputObject outputObjectException = new OutputObject();
			outputObjectException.setResult("INTERNAL_SERVER_ERROR");
			return new ResponseEntity<>(outputObjectException,HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
