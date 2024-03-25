package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - ????????
 */

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
