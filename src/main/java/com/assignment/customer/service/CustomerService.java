package com.assignment.customer.service;

import java.util.List;

import com.assignment.customer.bean.CustomerStatement;
import com.assignment.customer.bean.ErrorRecords;
import com.assignment.customer.bean.PostProcessingResult;

public interface CustomerService {
	
	public PostProcessingResult validateInputObject(CustomerStatement customerStatement,
			List<ErrorRecords> errorRecords, PostProcessingResult outputObject);
	
	public PostProcessingResult processCustomerRecords(List<CustomerStatement> customerStatements,
			List<ErrorRecords> errorRecords, PostProcessingResult finalResultPostProcessing);

}
