package com.assignment.customer.data;

import java.util.List;

import lombok.Data;

@Data
public class OutputObject {

	public OutputObject(String result, List<ErrorRecord> errorRecords) {
	this.result = result;
	this.errorRecords = errorRecords;
	}
	
	public OutputObject() {
		// TODO Auto-generated constructor stub
	}

	private String result;
	
	private List<ErrorRecord> errorRecords;
	
}
