package com.assignment.customer.controller;

import static com.assignment.customer.common.ApplicationConstants.BAD_REQUEST;
import static com.assignment.customer.common.ApplicationConstants.INTERNAL_SERVER_ERROR;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customer.bean.CustomerStatement;
import com.assignment.customer.bean.ErrorRecords;
import com.assignment.customer.bean.PostProcessingResult;
import com.assignment.customer.service.CustomerService;

/**
 * Controller to process Customer Statement API requests
 * 
 * @author Payel
 *
 */
@RequestMapping("/customers")
@RestController
@PropertySource("classpath:application.properties")
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
	@RequestMapping("/process-statement")
	public ResponseEntity<?> processCustomerRecord(@RequestBody List<CustomerStatement> customerStatements) {

		try {
			log.info("#######Enter processCustomerRecord()######",CustomerController.class);
			
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
			
			//This code block is used to throw Internal Server Error when description = "TEST_INTERNAL_SERVER_ERROR"
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

}
