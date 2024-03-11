package com.thelocalmarketplace.software;

import java.math.BigDecimal;

/**
 * Abstract class for methods of payment
 */

public abstract class IPayment {
    public abstract boolean processPayment(BigDecimal amount);
    private BigDecimal amountPaid;
    
    
    // Getter for amount paid in a specific payment
    public BigDecimal getAmountPaid() {
		return amountPaid;
	}


    
}