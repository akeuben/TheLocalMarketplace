package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.feature.ElectronicScaleFeature;
import com.thelocalmarketplace.software.payment.Transaction;

public class WaitingforBaggingState implements IUserSessionState<UserSessionState> {
	
	private final Mass MAXIMUM_MASS_DIFFERENCE = new Mass(BigInteger.valueOf(10*Mass.MICROGRAMS_PER_GRAM)); // Maximum weight discrepancy allowed

	@Override
	public UserSessionState onStateSet() {
		
		if(!SelfCheckout.getInstance().supportsFeature(ElectronicScaleFeature.class)){ // If the self-checkout does not have an electronic scale,
			return UserSessionState.READY_FOR_ITEM;									   // skip this state and go back to ReadyForItemState
		}
		
		Mass mass;
		try {
			mass = SelfCheckout.getInstance().getFeature(ElectronicScaleFeature.class).getScale().getCurrentMassOnTheScale();
		} catch (OverloadedDevice | RuntimeException e) { // Catch errors when retrieving mass from scale
			return null;
		}
		
		// Item is too light to initiate a mass change
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction(); // Get current transaction
		Mass expectedMass = currentTransaction.getExpectedMass(); // Get expected mass
		Mass absoluteDifference = expectedMass.difference(mass).abs(); // Compare expected and actual mass of item placed in bagging area
		
		if(absoluteDifference.compareTo(MAXIMUM_MASS_DIFFERENCE) == -1) { // If item falls within the scale's sensitivity window,
			return UserSessionState.READY_FOR_ITEM; 					  // go back to ReadyForItemState
		}
			
		return null; // If item is not the correct weight, weight for correct item to be placed/removed from bagging area
		
	}

	@Override
	public void onStateUnset() throws RuntimeException{}
	
	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		return null; // State does not change if user scans an additional item
	}
	
	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction(); // Get current transaction
		Mass expectedMass = currentTransaction.getExpectedMass(); // Get expected mass
		Mass absoluteDifference = expectedMass.difference(mass).abs(); // Compare expected and actual mass of item placed in bagging area
		
		if(absoluteDifference.compareTo(MAXIMUM_MASS_DIFFERENCE) == -1) { // If item falls within the scale's sensitivity window,
			return UserSessionState.READY_FOR_ITEM; 					  // go back to ReadyForItemState
		}
			
		return null; // If item is not the correct weight, weight for correct item to be placed/removed from bagging area
	}
	
	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		return null; // State does not change if user inserts coin
	}
}
