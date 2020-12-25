package com.assignment.customer.data;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author Payel
 *
 */
@Data
public class CustomerStatement implements Serializable {
	
	private Integer transactionReference;
	
	private String accountNumber;
	
	private BigDecimal startBalance;
	
	private BigDecimal mutation;
	
	private String description;
	
	private BigDecimal endBalance;
}
