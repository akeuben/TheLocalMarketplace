package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;

public abstract class AbstractCardPayment implements IPayment {

	private BigDecimal amountPaid; 
	
	public AbstractCardPayment(BigDecimal amountPaid) {
		this.amountPaid = amountPaid; 
	}

	@Override
	public BigDecimal getAmountPaid() {
		
		return this.amountPaid;
	}
	
	protected void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid; 
	}

}
