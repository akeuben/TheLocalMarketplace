package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

public class ReadyForPaymentState implements IUserSessionState<UserSessionState> {

	@Override
	public UserSessionState onStateSet() {
		return null; 	
	}

	@Override
	public void onStateUnset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		// TODO Auto-generated method stub
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
		if(absoluteDifference.compareTo(maximumDifference) == -1) {
			return UserSessionState.WAITING_FOR_BAGGING;
		}
		
		// The change in mass was within the margin of error. It is okay to
		// allow the customer to continue. Stay on the same state.
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

}
