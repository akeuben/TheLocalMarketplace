package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;

/**
 * Abstract class for methods of payment
 */

public interface IPayment {
    // Getter for amount paid in a specific payment
    public BigDecimal getAmountPaid();
}