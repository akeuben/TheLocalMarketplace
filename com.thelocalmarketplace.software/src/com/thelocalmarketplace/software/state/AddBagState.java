package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

public class AddBagState implements IUserSessionState {

	@Override
	public Object onStateSet() {
		//disable scanner to prevent any other items being added to transaction
		SelfCheckout.getInstance().getHardware().scanner.disable();
		
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction();
		Mass bagMass = new Mass(0);
		try {
			bagMass = SelfCheckout.getInstance().getHardware().baggingArea.getCurrentMassOnTheScale();
			bagMass = bagMass.difference(currentTransaction.getExpectedMass()).abs();
		} catch (OverloadedDevice|RuntimeException e) {
				return null;
		}
		currentTransaction.addBag(bagMass);
		return UserSessionState.WAITING_FOR_ATTENDANT;
	}

	@Override
	public void onStateUnset() {
	}

	@Override
	public Object onScanBarcode(Barcode barcode) {
		return null;
	}

	@Override
	public Object onWeightChanged(Mass mass) {
		return null;
	}

	@Override
	public Object onCoinInserted(BigDecimal value) {
		return null;
	}

}
