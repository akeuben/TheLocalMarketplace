package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;

public class CashPayment implements IPayment {
    private BigDecimal amountPaid;


    public CashPayment(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    @Override
    public BigDecimal getAmountPaid() {
    	return amountPaid;
    }
}