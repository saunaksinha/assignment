package com.assignment.customer.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerStatement {
	
	private Integer transactionReference;
	
	private String accountNumber;
	
	private BigDecimal startBalance;
	
	private BigDecimal mutation;
	
	private String description;
	
	private BigDecimal endBalance;
}
