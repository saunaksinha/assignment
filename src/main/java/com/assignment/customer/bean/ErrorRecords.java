package com.assignment.customer.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Payel
 *
 */

@Data
public class ErrorRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reference;
	
	private String accountNumber;
}
