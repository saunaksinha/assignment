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

import com.assignment.customer.bean.CustomerStatement;
import com.assignment.customer.bean.ErrorRecords;
import com.assignment.customer.bean.PostProcessingResult;
import com.assignment.customer.service.CustomerService;
import com.assignment.customer.service.impl.CustomerServiceImpl;

import static com.assignment.customer.common.ApplicationConstants.BAD_REQUEST;
import static com.assignment.customer.common.ApplicationConstants.INTERNAL_SERVER_ERROR;

/**
 * Controller to process Customer Statement API requests
 * 
 * @author Payel
 *
 */
@RequestMapping("/customers")
@RestController
public class CustomerController {

	Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	/**
	 * Method to validate Customer Statement for unique transaction reference number
	 * and correct value of end balance
	 * 
	 * @param customerStatements
	 * @return
	 */
	@RequestMapping("/process")
	public ResponseEntity<?> processCustomerRecord(@RequestBody List<CustomerStatement> customerStatements) {

		try {
			log.info("#######Enter processCustomerRecord()######", CustomerController.class);

			List<ErrorRecords> errorRecords = new ArrayList<ErrorRecords>();
			PostProcessingResult finalResultPostProcessing = new PostProcessingResult();

			Iterator<CustomerStatement> customerStatementList = customerStatements.iterator();
			// Validates the request payload JSON format for individual customer statement
			while (customerStatementList.hasNext()) {
				customerService.validateInputObject(customerStatementList.next(), errorRecords,
						finalResultPostProcessing);
			}
			// Bad Request response is generated 
			// in case the JSON validation for the request payload fails
			if (BAD_REQUEST.equals(finalResultPostProcessing.getResult())) {
				return new ResponseEntity<>(finalResultPostProcessing, HttpStatus.BAD_REQUEST);
			}
			// For a proper request payload, 
			// the processing of Customer Statement is initiated
			customerService.processCustomerRecords(customerStatements, errorRecords, finalResultPostProcessing);
			if (INTERNAL_SERVER_ERROR.equals(finalResultPostProcessing.getResult())) {
				return new ResponseEntity<>(finalResultPostProcessing, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(finalResultPostProcessing, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			PostProcessingResult outputObjectException = new PostProcessingResult();
			outputObjectException.setResult(INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(outputObjectException, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * private OutputObject checkDuplicateReference(CustomerStatement
	 * customerStatement, List<ErrorRecord> errorRecords, OutputObject outputObject)
	 * {
	 * 
	 * if (customerStatement.getTransactionReference() == 123) { ErrorRecord
	 * errorRecord = new ErrorRecord();
	 * errorRecord.setAccountNumber(customerStatement.getAccountNumber());
	 * errorRecord.setReference(customerStatement.getTransactionReference().toString
	 * ()); errorRecords.add(errorRecord);
	 * outputObject.setErrorRecords(errorRecords); if (outputObject.getResult() ==
	 * null) { outputObject.setResult("DUPLICATE_REFERENCE"); } else if
	 * (outputObject.getResult() == "INCORRECT_END_BALANCE") {
	 * outputObject.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE"); } }
	 * return outputObject; }
	 * 
	 * private OutputObject checkIncorrectBalance (CustomerStatement
	 * customerStatement, List<ErrorRecord> errorRecords, OutputObject outputObject)
	 * { if
	 * ((customerStatement.getStartBalance().add(customerStatement.getMutation())
	 * .compareTo(customerStatement.getEndBalance())) == 0) {
	 * System.out.println("Its a Match"); } else {
	 * System.out.println("Not a Match"); ErrorRecord errorRecord = new
	 * ErrorRecord();
	 * errorRecord.setAccountNumber(customerStatement.getAccountNumber());
	 * errorRecord.setReference(customerStatement.getTransactionReference().toString
	 * ()); errorRecords.add(errorRecord); if (outputObject.getResult() == null) {
	 * outputObject.setResult("INCORRECT_END_BALANCE"); } else if
	 * (outputObject.getResult() == "DUPLICATE_REFERENCE") {
	 * outputObject.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE"); }
	 * outputObject.setErrorRecords(errorRecords); } return outputObject; }
	 */

	/*
	 * public Set<Integer> findDuplicates(List<Integer> listContainingDuplicates) {
	 * final Set<Integer> setToReturn = new HashSet<>(); final Set<Integer> set1 =
	 * new HashSet<>();
	 * 
	 * for (Integer yourInt : listContainingDuplicates) { if (!set1.add(yourInt)) {
	 * setToReturn.add(yourInt); } } return setToReturn; }
	 */

}
