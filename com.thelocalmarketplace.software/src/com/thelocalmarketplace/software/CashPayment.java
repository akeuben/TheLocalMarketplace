package com.thelocalmarketplace.software;

import java.math.BigDecimal;

public class CashPayment extends IPayment {
    private BigDecimal amountPaid;


    public CashPayment(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal getAmountPayed() {
    	return amountPaid;
    }
}