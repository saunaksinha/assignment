package com.assignment.customer.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * Data object to map customer statement input details received as part of the request payload
 * @author Payel
 *
 */
@Data
public class CustomerStatement   {
	
	private static final long serialVersionUID = -2195208095271001929L;

	//Placeholder to maintain transaction reference number
	private Long transactionReference;
	
	//Placeholder to map customer's account number
	private String accountNumber;
	
	//Placeholder to store the starting balance
	private BigDecimal startBalance;
	
	//Placeholder to store the mutation data(can be either positive/negative)
	private BigDecimal mutation;
	
	//Placeholder to store Description
	private String description;
	
	//Placeholder to store the ending balance
	private BigDecimal endBalance;
}
