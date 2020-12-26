package com.assignment.customer.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author Payel
 *
 */
@Data
public class PostProcessingResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostProcessingResult(String result, List<ErrorRecords> errorRecords) {
	this.result = result;
	this.errorRecords = errorRecords;
	}
	
	public PostProcessingResult() {
	}

	private String result;
	
	private List<ErrorRecords> errorRecords;
	
}
