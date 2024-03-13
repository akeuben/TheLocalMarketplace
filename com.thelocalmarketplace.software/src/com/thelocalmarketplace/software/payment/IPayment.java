package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;

/**
 * Abstract class for methods of payment
 */

public abstract class IPayment {
    private BigDecimal amountPaid;
    
    
    // Getter for amount paid in a specific payment
    public BigDecimal getAmountPaid() {
		return amountPaid;
	}


    
}