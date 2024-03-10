package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;

public class CashPayment extends IPayment {
	private static long amount=0;
    private long amountPaid;
    private CoinValidatorObserverPayment observer;

    public CashPayment(long amountPaid) {
        this.amountPaid = amountPaid;
    }


    /**
     *
     * @param amount given
     * @return T/F the payment has been processed
     */
    @Override
    public boolean processPayment(CoinValidator cv) {
    	cv.attach(observer);
        if (amountPaid >= amount) {
            System.out.println("Cash Payment Accepted");
            return true;
        } else {
            System.out.println("Insufficent Cash Provided");
            return false;
        }

    }
    
    private static class CoinValidatorObserverPayment implements CoinValidatorObserver {

		@Override
		public void enabled(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnedOn(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnedOff(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void validCoinDetected(CoinValidator validator, BigDecimal value) {
			long val = value.longValue();
			amount = amount + val;
			
		}

		@Override
		public void invalidCoinDetected(CoinValidator validator) {
			// TODO Auto-generated method stub
			
		}
    	
    }


	@Override
	public boolean processPayment(long amount) {
        if (amountPaid >= amount) {
            System.out.println("Cash Payment Accepted");
            return true;
        } else {
            System.out.println("Insufficent Cash Provided");
            return false;
        }
	}

}