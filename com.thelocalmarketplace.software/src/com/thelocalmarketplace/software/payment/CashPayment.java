package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;

public class CashPayment extends Payment {
    private BigDecimal amountPaid;


    public CashPayment(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal getAmountPaid() {
    	return amountPaid;
    }
}