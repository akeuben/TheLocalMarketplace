package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.payment.Transaction;

public class ReadyForPaymentState implements IUserSessionState<UserSessionState> {

	@Override
	public UserSessionState onStateSet() {
		
		//Get current balance by creating a transaction instance
		Transaction transaction = SelfCheckout.getInstance().getCurrentSession().getTransaction(); 
		//Check if balance is 0 and that there is an item to end session 
		if (transaction.getTotalCost().compareTo(BigDecimal.ZERO) <= 0 && transaction.getProducts().length > 0) {
		        SelfCheckout.getInstance().endCurrentSession(); 
		    }
		//If item is at a 0, set state to ready for item
		    else if (transaction.getProducts().length == 0) {
		    	return UserSessionState.READY_FOR_ITEM;
		    }
		// Enable the coin slot to allow the user to insert a coin while the software
		// is in the correct state
		SelfCheckout.getInstance().getHardware().coinSlot.enable();
		return null; 
	}

	@Override
	public void onStateUnset() {}

	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		return null;
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		// Possible Weight Discrepancy
		
		// Get the relevant masses to compare
		Transaction transaction = SelfCheckout.getInstance().getCurrentSession().getTransaction();
		Mass expectedMass = transaction.getExpectedMass();
		Mass absoluteDifference = expectedMass.difference(mass).abs();
		
		// The maximum difference between masses.
		Mass maximumDifference = SelfCheckout.getInstance().getHardware().baggingArea.getSensitivityLimit();
		
		// Check if we are within the margin of error. If so, do nothing
		if(absoluteDifference.compareTo(maximumDifference) == 1) {
			return UserSessionState.WAITING_FOR_BAGGING;
		}
		
		// The change in mass was within the margin of error. It is okay to
		// allow the customer to continue. Stay on the same state.
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		
		//Create CoinPayment class instance
		CashPayment payment = new CashPayment(value);
		
		//Adding payment onto the current transaction 
		Transaction transaction = SelfCheckout.getInstance().getCurrentSession().getTransaction();
	    transaction.addPayment(payment);
	    
	    //Checking when the balance goes down to zero 
	    if (transaction.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) {
	        SelfCheckout.getInstance().endCurrentSession();
	    }
	    
	    return null;
	}

}
